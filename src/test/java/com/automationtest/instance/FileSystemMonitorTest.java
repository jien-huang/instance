package com.automationtest.instance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FileSystemMonitorTest {

  private FileSystemMonitor fileSystemMonitor;
  private File root;

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Before
  public void setUp() throws Exception {
    root = new File("scripts");
    if (!root.exists()) {
      root.mkdir();
    }
    File file1 = new File("scripts/test1.txt");
    file1.createNewFile();
    fileSystemMonitor = new FileSystemMonitor("scripts");
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @After
  public void tearDown() {
    File file1 = new File("scripts/test1.txt");
    if(file1.exists()){
      file1.delete();
    }
  }

  @Test
  public void exist() {
    Assert.assertTrue(fileSystemMonitor.exist("test1.txt"));
  }

  @Test
  public void getPath() {
    Assert.assertTrue(fileSystemMonitor.getPath().toString().equals("scripts"));
  }

}