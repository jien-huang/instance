package com.automationtest.instance;

import com.automationtest.instance.services.GitCenter;
import com.automationtest.instance.utils.Config;
import com.automationtest.instance.utils.Constants;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
class RestController {

    private final Logger logger = LoggerFactory.getLogger("RestController");
    @Autowired
    private
    GitCenter gitCenter;

    @GetMapping(value = "/ping")
    @ResponseBody
    public String ping() {
        logger.debug("we receive a ping.");
        final JsonObject json = new JsonObject();
        json.addProperty(Constants.STATUS, Constants.OK);
        return json.toString();
    }

    // get script list
    @GetMapping("getScriptList")
    @ResponseBody
    public String getScriptList() {
        final JsonObject json = new JsonObject();
        // Please implement here
        return json.toString();
    }

    @GetMapping("getConfiguration")
    @ResponseBody
    public String getConfiguration() {
        ;
        final JsonObject json = new JsonObject();
        final Properties properties = Config.getInstance().getAll();
        for (final Object key : properties.keySet()) {
            json.addProperty(key.toString(), properties.get(key).toString());
        }
        return json.toString();
    }

    // kick off script list
    @PostMapping("/RunScripts")
    @ResponseBody
    public String RunScripts(@RequestBody final List<String> scriptList) {
        final JsonObject json = new JsonObject();
        // Please implement here
        return json.toString();
    }

    // delete all (scripts and results)
    @DeleteMapping("/deleteAll")
    @ResponseBody
    public String deleteAll() {
        final JsonObject json = new JsonObject();
        // Please implement here
        return json.toString();
    }

    // delete all scripts
    @DeleteMapping("/deleteAllScripts")
    @ResponseBody
    public String deleteAllScripts() {
        final JsonObject json = new JsonObject();
        // Please implement here
        return json.toString();
    }

    // delete all results
    @DeleteMapping("/deleteAllResults")
    @ResponseBody
    public String deleteAllResults() {
        final JsonObject json = new JsonObject();
        // Please implement here
        return json.toString();
    }

    // download results ( zip whole folder, download it)
    @GetMapping("/downloadResults")
    public ResponseEntity<Resource> downloadResults() {
        // Load file as Resource
        final Resource resource = null; // resultsFolderMonitor.loadFileAsResource(fileName);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // list results (json tree structure)
    @GetMapping("getResultList")
    @ResponseBody
    public String getResultList() {
        final JsonObject json = new JsonObject();
        // Please implement here
        return json.toString();
    }

    // get git script folder: curl -LOk
    // https://github.com/jien-huang/instance/archive/addGit.zip can download, we
    // can handle it later
    @GetMapping("/downloadFromGit")
    @ResponseBody
    public String downloadFromGit() {
        final JsonObject json = new JsonObject();
        final String message = gitCenter.download() ? "OK" : "Download from git failed.";
        json.addProperty(Constants.MESSAGE, message);
        if (message.equals(Constants.OK)) {
            json.addProperty(Constants.STATUS, Constants.OK);
        } else {
            json.addProperty(Constants.STATUS, Constants.FAIL);
        }
        return json.toString();
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") final MultipartFile file) throws IOException {
        final String fileName = gitCenter.storeFile(file);

        final String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/scripts/")
                .path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file") final MultipartFile[] files)
            throws IOException {
        final List<UploadFileResponse> list = new ArrayList<>();
        for (final MultipartFile multipartFile : files) {
            final UploadFileResponse uploadFileResponse = uploadFile(multipartFile);
            list.add(uploadFileResponse);
        }
        return list;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable final String fileName,
            final HttpServletRequest request) {
        // Load file as Resource
        final Resource resource = gitCenter.loadFileAsResource(fileName);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
