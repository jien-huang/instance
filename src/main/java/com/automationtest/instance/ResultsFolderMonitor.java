package com.automationtest.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class ResultsFolderMonitor extends FolderMonitor {

  private static final String RESULTS = Config.getInstance().get("folder.results", "./results/").toString();

  @Autowired
  public ResultsFolderMonitor() throws IOException {
    fileSystemMonitor = new FileSystemMonitor(RESULTS);
  }

}
