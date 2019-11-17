package com.automationtest.instance;

import org.junit.*;

import java.io.IOException;

public class ScriptsFolderMonitorTest {

    ScriptsFolderMonitor scriptsFolderMonitor;

    @Before
    public void setup() {
        scriptsFolderMonitor = new ScriptsFolderMonitor();
    }

    @Test
    public void loadFileAsResource() {
        Assert.assertTrue(scriptsFolderMonitor.loadFileAsResource("testScript_1.js").getClass().getName().contains("Resource"));
    }

    @Test
    public void runScript() {
        Assert.assertTrue(scriptsFolderMonitor.runScript("testScript_1.js").contains("1st test"));
    }

    @After
    public void tearDown() {
        Utils.deleteFileOrFolder("results/testScript_1");
    }
}