package com.automationtest.instance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UploadFileResponseTest {

    UploadFileResponse uploadFileResponse;

    @Before
    public void setUp() {
        uploadFileResponse = new UploadFileResponse("fileName.txt", "http://localhost:3000/results/fileName.txt", "txt", 1024);
    }

    @Test
    public void getFileName() {
        Assert.assertEquals(uploadFileResponse.getFileName(), "fileName.txt");
    }

    @Test
    public void getFileDownloadUri() {
        Assert.assertTrue(uploadFileResponse.getFileDownloadUri().contains("fileName.txt"));
    }

    @Test
    public void getFileType() {
        Assert.assertEquals(uploadFileResponse.getFileType(), "txt");
    }

    @Test
    public void getSize() {
        Assert.assertEquals(uploadFileResponse.getSize(), 1024);
    }
}