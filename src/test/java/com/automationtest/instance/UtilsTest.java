package com.automationtest.instance;

import java.io.IOException;
import java.util.HashMap;

import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void toJson() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("hello", "my lord");
        Assert.assertTrue(Utils.isJson(Utils.toJson(map)));
    }

    @Test
    public void now() {
        Assert.assertTrue(DateUtil.parse(Utils.now()) != null);
    }

    @Test
    public void uuid() {
        Assert.assertTrue(Utils.uuid().length() > 10);
    }

    @Test
    public void randomDigit() {
        Assert.assertTrue(Integer.valueOf((Utils.randomDigit())) > 0);
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
            Assert.assertTrue("Got Exception", false);
        }
    }

    @Test
    public void jsonToYaml() {
        try {
            Assert.assertNull(Utils.jsonToYaml(""));
			Assert.assertTrue(Utils.isYaml(Utils.jsonToYaml("{\"status\":\"OK\"}")));
		} catch (IOException e) {
			Assert.assertTrue("Got Exception", false);
		}
    }
}