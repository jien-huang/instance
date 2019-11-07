package com.automationtest.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
class ScriptsFolderMonitor extends FolderMonitor {

  @Autowired
  public ScriptsFolderMonitor() throws IOException {
    fileSystemMonitor = new FileSystemMonitor("./scripts") ;
  }

  public String runScript(String scriptFileName) {
    try {
        // --video artifacts/videos
      Process process = Runtime.getRuntime().exec("testcafe \"chromium:headless\" " + scriptFileName);
      OutputStream out = process.getOutputStream();
      byte[] buf = new byte[4096];
      out.write(buf);
      out.flush();
      out.close();
      return new String(buf);
    } catch (IOException e) {
      return e.getMessage();
    }

  }
}
