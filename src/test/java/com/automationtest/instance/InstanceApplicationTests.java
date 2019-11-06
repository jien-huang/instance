package com.automationtest.instance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InstanceApplicationTests {

    @Autowired
    private RestController restController;

    @Autowired
    private WebController webController;

    @Test
    public void contextLoads() {
        assertThat(restController).isNotNull();
        assertThat(webController).isNotNull();
    }

    @Test
    public void applicationContextTest() {
        InstanceApplication.main(new String[] {});
    }

}
