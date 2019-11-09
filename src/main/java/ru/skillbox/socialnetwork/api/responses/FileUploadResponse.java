package ru.skillbox.socialnetwork.api.responses;

public class FileUploadResponse {

    private String id;
    private Integer ownerId;
    private String fileName;
    private String relativeFilePath;
    private String rawFileURL;
    private String fileFormat;
    private Long bytes;
    private FileType fileType;
    private Long createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativeFilePath() {
        return relativeFilePath;
    }

    public void setRelativeFilePath(String relativeFilePath) {
        this.relativeFilePath = relativeFilePath;
    }

    public String getRawFileURL() {
        return rawFileURL;
    }

    public void setRawFileURL(String rawFileURL) {
        this.rawFileURL = rawFileURL;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Long getBytes() {
        return bytes;
    }

    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
