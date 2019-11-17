package com.automationtest.instance;

import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.*;

public class ScriptsFolderMonitorTest {

    ScriptsFolderMonitor scriptsFolderMonitor;

    @Before
    public void setup() {
        try {
            scriptsFolderMonitor = new ScriptsFolderMonitor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadFileAsResource() throws IOException {
        Assert.assertTrue(scriptsFolderMonitor.loadFileAsResource("testScript_1.js").getClass().getName().contains("Resource"));
    }

    @Test
    public void runScript() {
        Assert.assertTrue( scriptsFolderMonitor.runScript("testScript_1.js").contains("1st test"));
    }

    @After
    public void tearDown() {
        Utils.deleteFileOrFolder("results/testScript_1");
    }
}