package com.automationtest.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
class ScriptsFolderMonitor extends FolderMonitor {

  private static final String SCRIPTS = Config.getInstance().get("folder.scripts", "./scripts/").toString();
  private static final String RESULTS = Config.getInstance().get("folder.results", "./results/").toString();
  private static final String VIDEO_OPTIONS = Config.getInstance().get("video.options", "--video " + RESULTS + " --video-options pathPattern=\"${DATE}_${TIME}_${TEST_INDEX}_${FILE_INDEX}.mp4\"").toString();


@Autowired
  public ScriptsFolderMonitor() throws IOException {
    fileSystemMonitor = new FileSystemMonitor(SCRIPTS) ;
  }

  public String runScript(String scriptFileName) {
    try {
      String cmd = "testcafe \"" + Config.getInstance().get("local.browser", "chrome") +":headless\" " + SCRIPTS + scriptFileName + " " + VIDEO_OPTIONS;
        // --video artifacts/videos
      Process process = Runtime.getRuntime().exec(cmd);
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
