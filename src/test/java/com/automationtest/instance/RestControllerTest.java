package com.automationtest.instance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InstanceApplication.class)
@WebAppConfiguration
public class RestControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup(){
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void ping() {

    try {
      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ping")).andReturn();
      Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("OK"));
    } catch (Exception e) {
      Assert.fail();
    }

  }

  @Test
  public void uploadMultiPartFiles() {
    MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", MediaType.TEXT_PLAIN_VALUE, "some xml".getBytes());
    MockMultipartFile secondFile = new MockMultipartFile("file", "other-file-name.data", MediaType.TEXT_PLAIN_VALUE, "some other type".getBytes());
    MockMultipartFile jsonFile = new MockMultipartFile("file", "test.json", MediaType.TEXT_PLAIN_VALUE, "{\"json\": \"someValue\"}".getBytes());

    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    try {
      mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadMultipleFiles").file(
              firstFile).file(
              secondFile).file(
              jsonFile)).andExpect(status().is(200));
    } catch (Exception e) {
      Assert.fail();
    }

  }

  @Test
  public void uploadFile() {
    MockMultipartFile jsonFile = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    try {
      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadFile").file(jsonFile)).andReturn();
      Assert.assertTrue(mvcResult.getResponse().getStatus() == 200);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void downloadFile() {
  }

  @After
  public void tearDown() {
    deleteFile("scripts/hello.txt");
    deleteFile("scripts/filename.txt");
    deleteFile("scripts/other-file-name.data");
    deleteFile("scripts/test.json");
  }

  private void deleteFile(String fileName) {
    File file1 = new File(fileName);
    if(file1.exists()){
      file1.delete();
    }
  }
}