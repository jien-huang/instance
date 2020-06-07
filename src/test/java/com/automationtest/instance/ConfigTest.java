package com.automationtest.instance;

import com.automationtest.instance.utils.Config;
import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void getInstance() {
        Assert.assertNotNull(Config.getInstance().get("client.id"));
    }

    @Test
    public void is() {
        Assert.assertTrue(Config.getInstance().is("debug"));
        Assert.assertFalse(Config.getInstance().is("notexistedValue"));
        Assert.assertTrue(Config.getInstance().is("new.notexisted", true));
    }

    @Test
    public void set() {
        Config.getInstance().set("headless", "true");
        Config.getInstance().set("browser", "chrome");
        Assert.assertTrue(Config.getInstance().is("headless"));
        Assert.assertTrue(Config.getInstance().is("headless", false));
        Assert.assertEquals(Config.getInstance().get("browser"), "chrome");
        Assert.assertEquals(Config.getInstance().get("browser", "firefox"), "chrome");
        Assert.assertEquals(Config.getInstance().get("new.browser", "firefox"), "firefox");

    }

    @Test
    public void getAll() {
        Assert.assertTrue(Config.getInstance().getAll().getClass().getName().contains("Properties"));
    }
}