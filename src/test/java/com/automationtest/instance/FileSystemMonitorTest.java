package com.automationtest.instance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class FileSystemMonitorTest {

  FileSystemMonitor fileSystemMonitor;
  File root;

  @Before
  public void setUp() throws Exception {
    root = new File("test");
    if(root.exists())
      root.delete();
    root.mkdir();
    File file1 = new File("test/test1.txt");
    file1.createNewFile();
    fileSystemMonitor = new FileSystemMonitor("test");
  }

  @After
  public void tearDown() throws Exception {
    root.delete();
  }

  @Test
  public void exist() {
    Assert.assertTrue(fileSystemMonitor.exist("test1.txt"));
  }
}