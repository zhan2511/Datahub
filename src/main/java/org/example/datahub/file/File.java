package org.example.datahub.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.example.datahub.common.persistent.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

//| 属性名        | 类型            | 约束     | 描述                       |
//| ---------- | ------------- | ------ | ------------------------ |
//| file_id    | bigint  | 主键，非空  | 文件记录唯一标识                 |
//| owner_id   | bigint  | 外键，非空  | 上传该文件的用户 ID              |
//| original_file_name  | varchar(255)  | 非空     | 原始文件名
//| saved_file_name     | varchar(255)  | 非空     | 存储在服务器上的文件名            |
//| file_path  | varchar(500)  | 非空     | 文件在服务器/存储中的路径            |
//| file_size  | bigint        | 非空     | 文件大小（字节）                 |
//| mime_type  | varchar(100)  | 非空     | 文件的 MIME 类型（如 image/png） |
@Entity
@Table(name = "files")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE files SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class File extends BaseEntity {
    @Column(name = "owner_id", nullable = false)
    Long ownerId;

    @Column(name = "original_file_name", nullable = false, length = 255)
    String originalFileName;

    @Column(name = "saved_file_name", nullable = false, length = 255)
    String savedFileName;

    @Column(name = "file_path", nullable = false, length = 500)
    String filePath;

    @Column(name = "file_size", nullable = false)
    Long fileSize;

    @Column(name = "mime_type", nullable = false, length = 100)
    String mimeType;

    // ================ Getters ================
    public Long getOwnerId() { return ownerId; }
    public String getOriginalFileName() { return originalFileName; }
    public String getSavedFileName() { return savedFileName; }
    public String getFilePath() { return filePath; }
    public Long getFileSize() { return fileSize; }
    public String getMimeType() { return mimeType; }

    // ================ Setters ================
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }
    public void setSavedFileName(String savedFileName) { this.savedFileName = savedFileName; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    // ================ Constructors ================
    public File() {
    }
    public File(Long ownerId, String originalFileName, String savedFileName, String filePath, Long fileSize, String mimeType) {
        this.ownerId = ownerId;
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
    }
}
