package com.automationtest.instance;

import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

  @Test
  public void toJson() {
  }

  @Test
  public void now() {
    Assert.assertTrue(DateUtil.parse(Utils.now())!=null);
  }

  @Test
  public void uuid() {
    Assert.assertTrue(Utils.uuid().length()>10);
  }

  @Test
  public void randomDigit() {
    Assert.assertTrue(Integer.valueOf((Utils.randomDigit()))>0);
  }

  @Test
  public void generatePassword() {
  }

  @Test
  public void isJson() {
    Assert.assertTrue(Utils.isJson("{\"status\":\"OK\"}"));
    Assert.assertFalse(Utils.isJson("This is not a json"));
  }

  @Test
  public void isYaml() {
  }

  @Test
  public void yamlToJson() {
  }

  @Test
  public void jsonToYaml() {
  }
}