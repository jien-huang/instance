package com.automationtest.instance;


import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

class FileSystemMonitor {
  private final Logger logger = LoggerFactory.getLogger("FileSystemMonitor");
  private final WatchService watchService = FileSystems.getDefault().newWatchService();
  private final HashMap<WatchKey, Path> keys = new HashMap<>();
  private final Path path;
  private final ConcurrentHashMap<String, String> fileList = new ConcurrentHashMap<>();

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public FileSystemMonitor(String watchPath) throws IOException {
    File folder = new File(watchPath);
    if(folder.exists()){
      if(!folder.isDirectory()){
        folder.delete();
        folder.mkdir();
      }
    } else {
      folder.mkdir();
    }
    path = Paths.get(watchPath);
    loadFileList();
    WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
    keys.put(key, path);
    new DefaultThreadFactory(watchPath, true).newThread(() -> {
      while (true) {
        WatchKey key1;
        try {
          key1 = watchService.take();}
        catch (InterruptedException e) {return;}
        Path dir = keys.get(key1);
        if(dir == null) {
          continue;
        }
        for(WatchEvent<?> event: key1.pollEvents()){
          Kind<?> kind = event.kind();
          if (kind == StandardWatchEventKinds.OVERFLOW){
            continue;
          }
          @SuppressWarnings("unchecked")
          WatchEvent<Path> ev = (WatchEvent<Path>)event;
          Path name = ev.context();
          logger.debug(name.toFile().getAbsolutePath());
          if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            fileList.put(name.toFile().getName(), name.toFile().getAbsolutePath());
          }
          if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
            fileList.remove(name.toFile().getName());
          }
        }
      }


    }).start();
  }

  private void loadFileList() throws IOException {
    Files.walk(path).sorted().forEach(p -> fileList.put(p.toFile().getName(), p.toFile().getAbsolutePath()));
  }

  public boolean exist(String fileName) {
    return fileList.containsKey(fileName) || fileList.containsValue(fileName);
  }

  public Path getPath() {
    return this.path;
  }
}
