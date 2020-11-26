package com.sys.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sys.core.base.Entity;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-11
 */
public class File extends Entity {

    private static final long serialVersionUID=1L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件路径
     */
    private String filePath;

    private String entityId;

    private String base64Type;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getBase64Type() {
        return base64Type;
    }

    public void setBase64Type(String base64Type) {
        this.base64Type = base64Type;
    }

    @Override
    public String toString() {
        return "File{" +
        "fileName=" + fileName +
        ", fileType=" + fileType +
        ", fileSize=" + fileSize +
        ", filePath=" + filePath +
        "}";
    }
}
