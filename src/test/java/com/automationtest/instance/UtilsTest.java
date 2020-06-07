package com.automationtest.instance;

import com.automationtest.instance.utils.Utils;
import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class UtilsTest {

    @Test
    public void constructorTest() {
        new Utils();
    }

    @Test
    public void toJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("hello", "my lord");
        Assert.assertTrue(Utils.isJson(Utils.toJson(map)));
    }

    @Test
    public void now() {
        Assert.assertNotNull(DateUtil.parse(Utils.now()));
    }

    @Test
    public void uuid() {
        Assert.assertTrue(Utils.uuid().length() > 10);
    }

    @Test
    public void randomDigit() {
        Assert.assertTrue(Integer.parseInt((Utils.randomDigit())) > 0);
    }

    @Test
    public void generatePassword() {
        Assert.assertTrue(Utils.generatePassword().length() > 0);
    }

    @Test
    public void isJson() {
        Assert.assertTrue(Utils.isJson("{\"status\":\"OK\"}"));
        Assert.assertFalse(Utils.isJson("This is not a json"));
    }

    @Test
    public void isYaml() {
        Assert.assertTrue(Utils.isYaml("root - child1"));
        Assert.assertFalse(Utils.isYaml("aws: elasticbeanstalk:container:nodejs: "));
    }

    @Test
    public void yamlToJson() {
        try {
            Assert.assertNull(Utils.yamlToJson(""));
            Assert.assertTrue(Utils.isJson(Utils.yamlToJson("root - child1")));
        } catch (IOException e) {
            Assert.fail("Got Exception");
        }
    }

    @Test
    public void jsonToYaml() {
        try {
            Assert.assertNull(Utils.jsonToYaml(""));
            Assert.assertTrue(Utils.isYaml(Utils.jsonToYaml("{\"status\":\"OK\"}")));
        } catch (IOException e) {
            Assert.fail("Got Exception");
        }
    }
}