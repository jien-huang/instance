package com.automationtest.instance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class FileSystemMonitorTest {

  private FileSystemMonitor fileSystemMonitor;
  private File root;

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Before
  public void setUp() throws Exception {
    fileSystemMonitor = new FileSystemMonitor("testFolder");
  }

  @Test
  public void exist() throws IOException, InterruptedException {
    File file1 = new File("testFolder/test1.txt");
    file1.createNewFile();
    TimeUnit timeUnit = TimeUnit.SECONDS;
    timeUnit.sleep(5);
    Assert.assertTrue(fileSystemMonitor.exist("test1.txt"));
    Assert.assertFalse(fileSystemMonitor.exist("somethingnotexisted"));
  }

  @Test
  public void getPath() {
    Assert.assertTrue(fileSystemMonitor.getPath().toString().equals("testFolder"));
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @After
  public void tearDown() throws InterruptedException {
    fileSystemMonitor.clearAll();
    TimeUnit timeUnit = TimeUnit.SECONDS;
    timeUnit.sleep(5);
    Assert.assertFalse(fileSystemMonitor.exist("test1.txt"));
    Utils.deleteFileOrFolder("testFolder");
    fileSystemMonitor.stop();
  }

}