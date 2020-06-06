package com.automationtest.instance;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

public class GitCenter {
    Git git;
    File path = Files.createTempDir();
    private static GitCenter _instance;

    private GitCenter(){}

    public static GitCenter getInstance() {
        if (_instance == null) {
            _instance = new GitCenter();
        }
        return _instance;
    }

    public void clean() {
        path.deleteOnExit();
    }

    public boolean download() throws IOException {

        if (git == null) {
            git = new Git(new FileRepository(path.getAbsolutePath()));
        }
        git.checkout().

        return true;
    }
    
}