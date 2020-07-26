package com.automationtest.instance;


import com.automationtest.instance.utils.Config;
import com.automationtest.instance.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

class FileSystemMonitor {
    private final Logger logger = LoggerFactory.getLogger("FileSystemMonitor");
    private final Path path;
    private ConcurrentHashMap<String, String> fileList = new ConcurrentHashMap<>();
    private final long quietPeriod = Integer.parseInt((String) Config.getInstance().get("idle.period", "1000"));
    private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private final FileService fileService = new FileService();

    public FileSystemMonitor(String watchPath) {
        File folder = new File(watchPath);
        if (folder.exists()) {
            if (!folder.isDirectory()) {
                folder.delete();
                folder.mkdir();
            }
        } else {
            folder.mkdir();
        }
        path = Paths.get(watchPath);
        fileList = loadFileList();

        new Thread(fileService).start();
    }

    public void stop() {
        this.fileService.stop();
    }

    class FileService implements Runnable {
        private volatile boolean exit = false;

        @Override
        public void run() {
            while (!exit) {
                try {
                    ConcurrentHashMap<String, String> tempMap = loadFileList();
                    if (isDifferent(tempMap, fileList)) {
                        fileList = tempMap;
                    } else {
                        timeUnit.sleep(quietPeriod);
                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        public void stop() {
            exit = true;
        }
    }

    private ConcurrentHashMap<String, String> loadFileList() {
        ConcurrentHashMap<String, String> newMap = new ConcurrentHashMap<>();
        if (!Files.exists(path)) {
            return newMap;
        }

        try {
            Files.walk(path).sorted().forEach(p -> newMap.put(p.toFile().getAbsolutePath(), p.toFile().getName()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return newMap;
    }

    private boolean isDifferent(ConcurrentHashMap<String, String> mapOne, ConcurrentHashMap<String, String> mapTwo) {
        if (!mapOne.keySet().equals(mapTwo.keySet())) {
            return true;
        }
        for (String key : mapOne.keySet()) {
            if (!mapOne.get(key).equals(mapTwo.get(key))) {
                return true;
            }
        }
        return false;
    }

    public boolean exist(String fileName) {
        return fileList.containsKey(fileName) || fileList.containsValue(fileName);
    }

    public void clearAll() {
        Utils.deleteFileOrFolder(this.path.toString());
        fileList.clear();
    }

    public Path getPath() {
        return this.path;
    }

}
