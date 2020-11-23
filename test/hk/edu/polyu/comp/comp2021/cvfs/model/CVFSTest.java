package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.operation.ConcreteOperation;
import hk.edu.polyu.comp.comp2021.cvfs.model.operation.Operation;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class CVFSTest {
    CVFS t1;

    private String show(CVFS t) {
        // show files in working directory
        StringBuilder sb = new StringBuilder();
        for (File f : t.list()) {
            sb.append(f.getFullName());
            sb.append(",");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    private String rshow(CVFS t) {
        // show all files in working directory
        StringBuilder sb = new StringBuilder();
        for (File f : t.rlist()) {
            sb.append(f.getFullName());
            sb.append(",");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
    private String showByCriterion(CVFS t, String criName){
        StringBuilder sb = new StringBuilder();
        for(File f:t.searchByCriterion(criName)){
            sb.append(f.getFullName());
            sb.append(",");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    private void init(){
        t1.newDoc("java","java","This is java file");
        t1.newDoc("html","html","Web");
        t1.newDoc("txt","txt","txtFile");
        t1.newDir("Folder1");
    }

    @Before
    public void before() {
        t1 = new CVFS();
        t1.newDisk(512);
    }


    @Test
    public void createDiskTest() {
        t1.newDisk(2048);
        Disk d = new ConcreteDisk(2048);
        assertEquals(2048,d.getCapacity());
        assertEquals(".",d.getWorkingDirName());
        d.makeDir("Folder1");
        d.changeDir("Folder1");
        assertEquals(d.getWorkingDirName(),"Folder1");
        assertEquals(d.toString(),"Disk{capacity=2048, root=Directory{name=./}, workingDir=Directory{name=Folder1/}}");
    }


    @Test
    public void newDocTest1() {
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        boolean errored = false;
        try { // test invalid name
            t1.newDoc("A@!#!", "Txt", "Testing");

        } catch (Exception e) {
            errored = true;
        }
        assertTrue(errored);
        assertEquals(show(t1), "TestHtml.html,TestTXT.txt");
    }


    @Test
    public void newDocTest2() {
        // try write some thing very long
        boolean errored = false;
        try {
            t1.newDoc("Test", "TXT",
                    "TESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTINGTESTING");
        } catch (Exception e) {
            errored = true;
        }
        assertTrue(errored);
    }

    @Test
    public void newDocTest3() {

        boolean errored = false;
        try { // test invalid name
            t1.newDoc("aaaaaaaaaaaaaaaaaa", "Txt", "Testing");

        } catch (Exception e) {
            errored = true;
        }
        assertTrue(errored);
    }


    @Test
    public void newDocTest4() {

        boolean errored = false;
        try { // test invalid name
            t1.newDoc("", "Txt", "Testing");

        } catch (Exception e) {
            errored = true;
        }
        assertTrue(errored);
    }
    @Test
    public void fileTypeTest1() {
        FileType a =FileType.initType("txt");
        FileType b = FileType.initType("hTml");
        FileType c = FileType.initType("tXT");
        FileType d = FileType.initType("java");
        FileType f = FileType.initType("css");
        assertEquals(a,c);
        assertEquals(a,a);
        assertNotEquals(a,new CVFS());
        assertNotEquals(a,null);
        assertEquals(b.toString(),"html");
        assertEquals(c.toString(),"txt");
        assertEquals(d.toString(),"java");
        assertEquals(f.toString(),"css");

        boolean errored = false;
        try {
            FileType e = FileType.initType("cpp");
        } catch (Exception e) {
            errored = true;
        }
        assertTrue(errored);
    }



    @Test
    public void newDirTest1() {
        t1.newDir("Folder1");
        assertEquals(show(t1), "Folder1/");
    }

    @Test
    public void newDirTest2() {
        t1.newDir("Folder1");
        boolean errored = false;
        try {
            t1.newDir("Folder1");
        } catch (Exception e) {
            errored =true;
        }
        assertTrue(errored);

    }
    @Test
    public void cdTest(){
        init();
        assertEquals(t1.getPath(),"./");
        t1.changeDir("Folder1");
        assertEquals(t1.getPath(),"./Folder1/");
        boolean errored = false;
        try {
            t1.changeDir("dsaf");
        } catch (Exception e) {
            errored =true;
        }
        assertTrue(errored);
    }

    @Test
    public void delFileTest1() {
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDir("Folder1");
        assertEquals(show(t1), "TestTXT.txt,Folder1/");
        t1.delFile("TestTXT");
        assertEquals(show(t1), "Folder1/");
        t1.delFile("Folder1");
        assertEquals(show(t1), "");
    }

    @Test
    public void delFileTest2() {
        // test recursive del a folder

        t1.newDir("Folder1");
        t1.changeDir("Folder1");
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestTXT2", "TXT", "TESTING");
        assertEquals(show(t1), "TestTXT2.txt,TestTXT.txt");
        t1.changeDir("..");
        assertNotEquals(rshow(t1), "");
        t1.delFile("Folder1");
        assertEquals(rshow(t1), "");
    }

    @Test
    public void renameTest() {
        t1.newDoc("TestTXT", "TXT", "TESTING");
        assertEquals(show(t1), "TestTXT.txt");
        t1.rename("TestTXT", "Renamed");
        assertEquals(show(t1), "Renamed.txt");
    }

    @Test
    public void undoTest1() {
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        t1.undo();
        assertEquals(show(t1), "TestTXT.txt");
        t1.undo();
        assertEquals(show(t1), "");
        boolean errored = false;
        try {
            t1.undo();
        } catch (EmptyStackException e) {
           errored = true;
        }
        assertTrue(errored);
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
        assertEquals(show(t1), "TestHtml.html,TestTXT.txt");
    }

    @Test
    public void redoTest2() {
        t1.newDoc("TestTXT", "TXT", "TESTING");
        t1.newDoc("TestHtml", "Html", "TESTING");
        t1.undo();
        t1.newDir("Test");
        t1.redo();
        t1.undo();
        t1.undo();
        assertEquals(show(t1), "TestTXT.txt");
        t1.redo();
        assertEquals(show(t1), "TestTXT.txt,Test/");
    }

    @Test
    public void testSaveLoad() {
        init();
        t1.store();
        CVFS t2 = new CVFS();
        t2.load();
        assertEquals(show(t2), "txt.txt,html.html,java.java,Folder1/");
    }



    @Test
    public void newCriTest() {
        t1.newSimpleCri("AA", "size", ">", "100");
        t1.newSimpleCri("BB", "name", "contains", "\"File\"");
        t1.newBinaryCri("AB", "AA", "&&", "BB");
        t1.newBinaryCri("CC", "AB", "||", "BB");
        t1.printAllCriteria();
    }
    @Test
    public void criNameTest1() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("a","size","==","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }
    @Test
    public void criNameTest2() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri(null,"size","==","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }
    @Test
    public void criNameTest3() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("*!","size","==","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }


    @Test
    public void criOpTest1() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("AA","error","==","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }
    @Test
    public void criValTest1() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("AA","name","==","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }

    @Test
    public void criValTest2() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("AA","size","==","123dsfafew");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }

    @Test
    public void criAttrTest1() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("AA",null,"==","123dsfafew");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }
    @Test
    public void criOpTest3() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("AA","name","contains","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }
    @Test
    public void criOpTest4() {
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri(null,"name","contains","10");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);
    }


    @Test
    public void simpleNegationCriTest() {
        t1.newDoc("small", "txt", "small");
        t1.newDoc("huge", "txt", "This is a huge file, please be careful!!!");
        show(t1);
        t1.newSimpleCri("AA", "size", "<", "70");
        // t1.newSimpleCri("AA","size","<","70");
        t1.newNegation("AB", "AA");
//        assertEquals(t1.printAllCriteria(),"isDocument\n" +
//                "size < 70\n" +
//                "!(size < 70)\n");

        assertEquals(showByCriterion(t1,"AA"),"small.txt");
        assertEquals(showByCriterion(t1,"AB"),"huge.txt");
    }

    @Test
    public void simpleNameContainsCriTest(){
        t1.newDoc("ABCD","html","\"ABCDEFGH\"");
        t1.newSimpleCri("AA","name","contains","\"BC\"");
        assertEquals(showByCriterion(t1,"AA"),"ABCD.html");
        t1.newNegation("AB","AA");
        assertEquals(showByCriterion(t1,"AB"),"");
    }

    @Test
    public void typeEqualsCriTest(){
        init();
        t1.newSimpleCri("AA","type","equals","\"HtMl\"");
        assertEquals(showByCriterion(t1,"AA"),"html.html");
        t1.newNegation("AB","AA");
        assertEquals("txt.txt,java.java,Folder1/",showByCriterion(t1,"AB"));
    }

    @Test
    public void sizelargerEqualsCriTest(){
        init();
        t1.newSimpleCri("AA","size",">=","46");
        assertEquals("txt.txt,html.html,java.java",showByCriterion(t1,"AA"));
        t1.newNegation("AB","AA");
        assertEquals("Folder1/",showByCriterion(t1,"AB"));
    }

    @Test
    public void sizelargerCriTest(){
        init();
        t1.newSimpleCri("AA","size",">","46");
        assertEquals("txt.txt,java.java",showByCriterion(t1,"AA"));
        t1.newNegation("AB","AA");
        assertEquals("html.html,Folder1/",showByCriterion(t1,"AB"));
    }

    @Test
    public void sizeEqualsTest(){
        init();
        t1.newSimpleCri("AA","size","==","46");
        t1.newSimpleCri("AB","size","!=","46");
        assertEquals("html.html",showByCriterion(t1,"AA"));
        assertEquals("txt.txt,java.java,Folder1/",showByCriterion(t1,"AB"));
    }
    @Test
    public void sizeLessEqualsTest(){
        init();
        t1.newSimpleCri("AA","size","<=","46");
        assertEquals("html.html,Folder1/",showByCriterion(t1,"AA"));

    }
    @Test
    public void typeBinaryCriTest1(){
        init();
        t1.newSimpleCri("AA","type","equals","\"HtMl\"");
        t1.newSimpleCri("BB","name","contains","\"ml\"");
        t1.newBinaryCri("AB","AA", "&&", "BB");
        assertEquals("isDocument\n" +
                "type equals HtMl\n" +
                "name contains ml\n" +
                "(type equals HtMl) && (name contains ml)\n",t1.printAllCriteria());
        assertEquals("html.html",showByCriterion(t1,"AB"));

    }
    @Test
    public void typeBinaryCriTest2(){
        init();
        t1.newSimpleCri("AA","type","equals","\"HtMl\"");
        t1.newSimpleCri("BB","name","contains","\"java\"");
        t1.newBinaryCri("AB","AA", "||", "BB");
        assertEquals("isDocument\n" +
                "type equals HtMl\n" +
                "name contains java\n" +
                "(type equals HtMl) || (name contains java)\n",t1.printAllCriteria());
        assertEquals("html.html,java.java",showByCriterion(t1,"AB"));
    }
    @Test
    public void isDocumentTest2(){
        init();
        assertEquals("txt.txt,html.html,java.java",showByCriterion(t1,"isDocument"));
    }

    @Test
    public void duplicateCriTest(){
        t1.newSimpleCri("AA","type","equals","\"HtMl\"");
        boolean errored = false;
        try { // test invalid name
            t1.newSimpleCri("AA","type","equals","\"HtMl\"");

        } catch (IllegalArgumentException e) {
            errored = true;
        }
        assertTrue(errored);

    }
    @Test
    public void pathTest(){
        init();
        assertEquals("./",t1.getPath());
        t1.changeDir("Folder1");
        assertEquals("./Folder1/",t1.getPath());
    }

}
