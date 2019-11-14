package com.automationtest.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

@Service
class ScriptsFolderMonitor extends FolderMonitor {

  private static final String SCRIPTS = Config.getInstance().get("folder.scripts", "./scripts/").toString();
  private static final String RESULTS = Config.getInstance().get("folder.results", "./results/").toString();
  private static final String VIDEO_OPTIONS = Config.getInstance().get("video.options", " --video-options pathPattern='${DATE}-${TIME}-Test${TEST_INDEX}-No${FILE_INDEX}.mp4'").toString();


@Autowired
  public ScriptsFolderMonitor() throws IOException {
    fileSystemMonitor = new FileSystemMonitor(SCRIPTS) ;
  }

  public String runScript(String scriptFileName) {
    try {
      String resultFolder = RESULTS + scriptFileName.replaceAll(".js", "").replaceAll(".ts", "");
      String cmd = "testcafe \"" + Config.getInstance().get("local.browser", "chrome") +":headless\" "
              + SCRIPTS + scriptFileName + " --reporter json  --video "
              + resultFolder
              + VIDEO_OPTIONS;

      Set<PosixFilePermission> perms = new HashSet<>();
      // add permission as rwxr--r-- 744
      perms.add(PosixFilePermission.OWNER_WRITE);
      perms.add(PosixFilePermission.OWNER_READ);
      perms.add(PosixFilePermission.OWNER_EXECUTE);
      perms.add(PosixFilePermission.GROUP_READ);
      perms.add(PosixFilePermission.OTHERS_READ);
      FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(perms);
      Path path;
      if(Config.getInstance().get("os.name","Linux").toString().toLowerCase().contains("win")){
        path = Files.createTempFile(Paths.get("."), "test", ".bat");
      } else {
        path = Files.createTempFile(Paths.get("."), "test", ".sh", fileAttributes);
      }
      Files.write(path, cmd.getBytes());
      Process process = Runtime.getRuntime().exec(path.toString());
      BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line, outputLine = "";
      line = in.readLine();
      line = in.readLine();

      while ((line = in.readLine()) != null) {
        outputLine += line + System.lineSeparator();
      }
      process.waitFor();
      Files.deleteIfExists(path);
      // if report.json not existed, create it; then append report into it.
      Files.write(Paths.get(resultFolder + "/report.json"), outputLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      return outputLine;
    } catch (IOException | InterruptedException e) {
      return e.getMessage();
    }

  }


}
