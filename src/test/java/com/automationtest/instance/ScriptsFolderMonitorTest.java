package com.automationtest.instance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void downloadFromGit() {
        Assert.assertFalse(scriptsFolderMonitor.downloadFromGit().contains(Constants.OK));
        Config.getInstance().set("git.repository.url","https://github.com/jien-huang/instance");
        Assert.assertFalse(scriptsFolderMonitor.downloadFromGit().contains(Constants.OK));
        Config.getInstance().set("git.repository.folder","gitScriptsFolderExample");
    }

    @After
    public void tearDown() {
        Utils.deleteFileOrFolder("results/testScript_1");
    }
}