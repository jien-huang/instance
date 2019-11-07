package com.automationtest.instance;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
public class RestController {

  private final Logger logger = LoggerFactory.getLogger("RestController");
  @Autowired
  ResultsFolderMonitor resultsFolderMonitor;
  @Autowired
  ScriptsFolderMonitor scriptsFolderMonitor;

  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  @ResponseBody
  public String ping(){
    JsonObject json = new JsonObject();
    json.addProperty(Constants.STATUS, Constants.OK);
    return json.toString();
  }

  @PostMapping("/uploadFile")
  public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    String fileName = scriptsFolderMonitor.storeFile(file);

    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/downloadFile/")
      .path(fileName)
      .toUriString();

    return new UploadFileResponse(fileName, fileDownloadUri,
      file.getContentType(), file.getSize());
  }

  @PostMapping("/uploadMultipleFiles")
  public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
    List<UploadFileResponse> list = new ArrayList<>();
    for (MultipartFile multipartFile : files) {
      UploadFileResponse uploadFileResponse = uploadFile(multipartFile);
      list.add(uploadFileResponse);
    }
    return list;
  }

  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
    // Load file as Resource
    Resource resource = resultsFolderMonitor.loadFileAsResource(fileName);

    // Try to determine file's content type
    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      logger.info("Could not determine file type.");
    }

    // Fallback to the default content type if type could not be determined
    if(contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(contentType))
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
      .body(resource);
  }
}
