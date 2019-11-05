package com.automationtest.instance;


import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FileSystemMonitor {
  private Logger logger = LoggerFactory.getLogger("FileSystemMonitor");
  final WatchService watchService = FileSystems.getDefault().newWatchService();
  final HashMap<WatchKey, Path> keys = new HashMap<>();
  Path path;
  ConcurrentHashMap<String, String> fileList = new ConcurrentHashMap<>();

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
    new DefaultThreadFactory(watchPath, true).newThread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          WatchKey key;
          try {key = watchService.take();}
          catch (InterruptedException e) {return;}
          Path dir = keys.get(key);
          if(dir == null) {
            continue;
          }
          for(WatchEvent<?> event: key.pollEvents()){
            WatchEvent.Kind kind = event.kind();
            if (kind == StandardWatchEventKinds.OVERFLOW){
              continue;
            }
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


      }
    }).start();
  }

  private void loadFileList() throws IOException {
    Files.walk(path).sorted().forEach(p ->{
      fileList.put(p.toFile().getName(), p.toFile().getAbsolutePath());
    });
  }

  public boolean exist(String fileName) {
    return fileList.containsKey(fileName) || fileList.containsValue(fileName);
  }

  public Path getPath() {
    return this.path;
  }
}
