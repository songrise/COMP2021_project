package SampleProject.hk.edu.polyu.comp.comp2021.cvfs.model;
import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.model.File;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class CVFSTest {
    CVFS t1;
    private String show(CVFS t){
        //show files in working directory
        StringBuilder sb = new StringBuilder();
        for(File f :t.list()){
            sb.append(f.getFullName());
            sb.append(",");
        }
        if(sb.length()>0)
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    private String rshow(CVFS t){
        //show all files in working directory
        StringBuilder sb = new StringBuilder();
        for(File f :t.rlist()){
            sb.append(f.getFullName());
            sb.append(",");
        }
        if(sb.length()>0)
            sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }


    @Before
    public void before(){
        t1 =  new CVFS();
        t1.newDisk(512);
    }

    @Test
    public void createDiskTest() {
        t1.newDisk(2048);
    }

    @Test
    public void newDocTest1(){
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        boolean f = false;
        try{ //test invalid name
            t1.newDoc("@!#!","Txt","Testing");
            f = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        assertFalse(f);
        assertEquals(show(t1),"TestTXT.txt,TestHtml.html");
    }

    @Test
    public void newDocTest2(){
        // try write some thing very long
        try{
            t1.newDoc("Test","TXT","TESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTING");
        }
        catch (OutOfMemoryError e){
        e.printStackTrace();
        }
    }

    @Test
    public void newDirTest1(){
        t1.newDir("Folder1");
        assertEquals(show(t1),"Folder1/");
    }

    @Test
    public void newDirTest2(){
        t1.newDir("Folder1");
        try{
            t1.newDir("Folder1");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void delFileTest1(){
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDir("Folder1");
        assertEquals(show(t1),"TestTXT.txt,Folder1/");
        t1.delFile("TestTXT");
        assertEquals(show(t1),"Folder1/");
        t1.delFile("Folder1");
        assertEquals(show(t1),"");
    }
@Test
    public void delFileTest2(){
        //test recursive del a folder

        t1.newDir("Folder1");
        t1.changeDir("Folder1");
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestTXT2", "TXT", "TESTING");
        assertEquals(show(t1),"TestTXT.txt,TestTXT2.txt");
        t1.changeDir("..");
        assertNotEquals(rshow(t1),"");
        t1.delFile("Folder1");
        assertEquals(rshow(t1),"");
    }


    @Test public void renameTest(){
        t1.newDoc("TestTXT", "TXT", "TESTING");
        assertEquals(show(t1),"TestTXT.txt");
        t1.rename("TestTXT","Renamed");
        assertEquals(show(t1),"Renamed.txt");
    }

    @Test
    public void undoTest1(){
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        t1.undo();
        assertEquals(show(t1),"TestTXT.txt");
        t1.undo();
        assertEquals(show(t1),"");
        try{
            t1.undo();
        }
        catch (EmptyStackException e){
            e.printStackTrace();
        }
    }

    @Test
    public void redoTest1() {
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        t1.undo();
        t1.undo();
        t1.redo();
        assertEquals(show(t1), "TestTXT.txt");
        t1.redo();
        assertEquals(show(t1), "TestTXT.txt,TestHtml.html");
    }
        @Test
        public void redoTest2(){
            t1.newDoc("TestTXT", "TXT", "TESTING");
            t1.newDoc("TestHtml", "Html", "TESTING");
            t1.undo();
            t1.newDir("Test");
            t1.redo();
            t1.undo();
            t1.undo();
            assertEquals(show(t1),"TestTXT.txt");
            t1.redo();
            assertEquals(show(t1),"TestTXT.txt,Test/");
    }

    @Test
    public void testSaveLoad(){
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        t1.store();
        CVFS t2 = new CVFS();
        t2.load();
        assertEquals(show(t2), "TestTXT.txt,TestHtml.html");
    }
}
