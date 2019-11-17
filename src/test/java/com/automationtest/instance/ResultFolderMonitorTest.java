package com.automationtest.instance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ResultFolderMonitorTest {

    ResultsFolderMonitor resultsFolderMonitor;

    @Before
    public void setup() throws IOException {
        resultsFolderMonitor = new ResultsFolderMonitor();
        Assert.assertNotNull(resultsFolderMonitor);
    }

    @Test
    public void loadFileAsResource() {

        Assert.assertTrue(resultsFolderMonitor.loadFileAsResource("testForDownload.txt").getClass().getName().contains("Resource"));
        Assert.assertNull(resultsFolderMonitor.loadFileAsResource("somethingnotexisted.file"));
        Assert.assertNull(resultsFolderMonitor.loadFileAsResource("malformat-..%%URL_*~somethingnotexisted.file"));
    }

    @Test
    public void storeFile() {
        MultipartFile multipartFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        try {
            resultsFolderMonitor.storeFile(multipartFile);
            resultsFolderMonitor.fileSystemMonitor.exist("filename.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        Utils.deleteFileOrFolder("results/filename.txt");
    }
}