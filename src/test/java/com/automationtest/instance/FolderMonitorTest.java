package com.automationtest.instance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;

import static org.junit.Assert.*;

public class FolderMonitorTest {

    FolderMonitor folderMonitor;
    @Before
    public void setup() {
        try {
            folderMonitor = new FolderMonitor();
            Assert.assertNotNull(folderMonitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadFileAsResource() {
        try {
            Assert.assertTrue(folderMonitor.loadFileAsResource("testForDownload.txt").getClass().getName().contains("Resource"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storeFile() {
        MultipartFile multipartFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        try {
            folderMonitor.storeFile(multipartFile);
            folderMonitor.fileSystemMonitor.exist("filename.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}