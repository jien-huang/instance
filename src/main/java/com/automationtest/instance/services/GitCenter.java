package com.automationtest.instance.services;

import com.automationtest.instance.utils.Config;
import com.automationtest.instance.utils.Utils;
import org.eclipse.jgit.api.Git;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class GitCenter {

    private static final String RESULTS = Config.getInstance().get("folder.results", "./results/").toString();
    Git git;

    Path path;

    public void clean() {
        Utils.deleteFileOrFolder(this.path.toString());;
    }

    // download from github or git server
    // provide git url and branch
    public boolean download() {


        throw new UnsupportedOperationException("Not Implemented yet");
    }

    public boolean getFile() {

        throw new UnsupportedOperationException("Not Implemented yet");
    }

    public List<String> getFileList() {
        throw new UnsupportedOperationException("Not Implemented yet");
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.getPath().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = this.getPath().resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public Path getPath() {
        return this.path;
    }
    
}