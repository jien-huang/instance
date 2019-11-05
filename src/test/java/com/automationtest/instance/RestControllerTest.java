package com.automationtest.instance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void ping() {
    assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ping",
      String.class)).contains("OK");
  }

  @Test
  public void uploadFile() {
  }

  @Test
  public void uploadMultipleFiles() {
  }

  @Test
  public void downloadFile() {
  }
}