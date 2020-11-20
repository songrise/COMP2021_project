package SampleProject.hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.Directory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectoryTest{
    Directory root;

    @Before
    public void createRoot(){
        this.root = Directory.createRoot();
    }

    @Test
    public void TypeTest1(){
        assertEquals(root.getType(),"directory");
    }

    @Test
    public void TypeTest2(){
      assertTrue(root.isDirectory());
    }

    @Test
    public void NameTest1(){
        assertNull(root.getName());
    }
    }
