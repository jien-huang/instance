package com.automationtest.instance;

class UploadFileResponse {
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;

  public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
    this.setFileName(fileName);
    this.setFileDownloadUri(fileDownloadUri);
    this.setFileType(fileType);
    this.setSize(size);
  }

public String getFileName() {
	return fileName;
}

private void setFileName(String fileName) {
	this.fileName = fileName;
}

public String getFileDownloadUri() {
	return fileDownloadUri;
}

private void setFileDownloadUri(String fileDownloadUri) {
	this.fileDownloadUri = fileDownloadUri;
}

public String getFileType() {
	return fileType;
}

private void setFileType(String fileType) {
	this.fileType = fileType;
}

public long getSize() {
	return size;
}

private void setSize(long size) {
	this.size = size;
}

}
