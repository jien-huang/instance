package com.automationtest.instance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
class ScriptsFolderMonitor extends FolderMonitor {

    private static final String SCRIPTS = Config.getInstance().get("folder.scripts", "./scripts/").toString();
    private static final String RESULTS = Config.getInstance().get("folder.results", "./results/").toString();
    private static final String VIDEO_OPTIONS = Config.getInstance().get("video.options",
            " --video-options pathPattern=${DATE}-${TIME}-Test${TEST_INDEX}-No${FILE_INDEX}.mp4").toString();

    @Autowired
    public ScriptsFolderMonitor() {
        fileSystemMonitor = new FileSystemMonitor(SCRIPTS);
    }

    public String runScript(String scriptFileName) {
        try {
            String resultFolder = RESULTS + scriptFileName.replaceAll(".js", "").replaceAll(".ts", "");
            String outputLine = callScript(scriptFileName, resultFolder);
            // if report.json not existed, create it;
            // append report into it.
            Files.write(Paths.get(resultFolder + "/report.json"), outputLine.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            return outputLine;
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }

    }

    private String callScript(String scriptFileName, String resultFolder) throws IOException, InterruptedException {
        String cmd = "testcafe \"" + Config.getInstance().get("local.browser", "chrome") + ":headless\" " + SCRIPTS
                + scriptFileName + " --reporter json  --video " + resultFolder + VIDEO_OPTIONS;

        return callCommand(cmd);
    }

    private String callCommand(String cmd) throws IOException, InterruptedException {
        Path path;
        if (Config.getInstance().get("os.name", "Linux").toString().toLowerCase().contains("win")) {
            path = Files.createTempFile(Paths.get("."), "test", ".bat");
        } else {
            // add permission as rwxr--r-- 744
            Set<PosixFilePermission> perms = new HashSet<>(Arrays.asList(PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ, PosixFilePermission.GROUP_READ));
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(perms);
            path = Files.createTempFile(Paths.get("."), "test", ".sh", fileAttributes);
        }
        Files.write(path, cmd.getBytes());
        Process process = Runtime.getRuntime().exec(path.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder outputLine = new StringBuilder();
        line = in.readLine();
//        line = in.readLine();

        while ((line = in.readLine()) != null) {
            outputLine.append(line).append(System.lineSeparator());
        }
        int ret_code = process.waitFor();
        Files.deleteIfExists(path);
        if (ret_code != 0) {
            throw new InterruptedIOException("command failed. return code = " + ret_code);
        }
        return outputLine.toString();
    }

    // get git script folder: curl -LOk https://github.com/jien-huang/instance/archive/addGit.zip can download, we can handle it
    public String downloadFromGit() {
        String tmpdir = Config.getInstance().get("java.io.tmpdir").toString();
        Object git_repo_url = Config.getInstance().get("git.repository.url");
        if(git_repo_url == null){
            return "Please set git.repository.url=https://[git website]/[user name]/[repository name]";
        }
        Object git_repo_folder = Config.getInstance().get("git.repository.foler");
        if(git_repo_folder == null) {
            return "Please set git.repository.folder=[scripts folder under repository]";
        }

        try {
            String gitBranchName = Config.getInstance().get("git.repository.branch", "master").toString();
            String whole_curl_cmd = "curl -LOk " + git_repo_url.toString()
                    + "/archive/" + gitBranchName + ".zip > "
                    + tmpdir;
            callCommand(whole_curl_cmd);
            String destFolder = tmpdir + Utils.randomDigit() + "/";
            Utils.unzipFile( tmpdir+ gitBranchName + ".zip", destFolder);
            FileSystemUtils.copyRecursively(Paths.get(destFolder), Paths.get(SCRIPTS));
        } catch (IOException | InterruptedException e) {
            return "curl download scripts from git failed. " + e.getMessage();
        }
        return Constants.OK;
    }
}
