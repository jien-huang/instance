package com.automationtest.instance;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FolderMonitor {
  FileSystemMonitor fileSystemMonitor;

  public Resource loadFileAsResource(String fileName) throws IOException {
    try {
      Path filePath = this.fileSystemMonitor.getPath().resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new FileNotFoundException("File not found " + fileName);
      }
    } catch (MalformedURLException | FileNotFoundException ex) {
      throw new IOException("File not found " + fileName, ex);
    }
  }

  public String storeFile(MultipartFile file) throws IOException {
    // Normalize file name
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));


    // Check if the file's name contains invalid characters
    if (fileName.contains("..")) {
      throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
    }

    // Copy file to the target location (Replacing existing file with the same name)
    Path targetLocation = this.fileSystemMonitor.getPath().resolve(fileName);
    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    return fileName;

  }
}
