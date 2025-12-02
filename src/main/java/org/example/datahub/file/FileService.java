package org.example.datahub.file;


import jakarta.annotation.PostConstruct;
import org.example.datahub.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {
    private final FileRepository fileRepository;

    private final Path uploadDir;

    @Autowired
    public FileService(FileRepository fileRepository, @Value("${app.upload.dir}") String uploadDirConfig) {
        this.fileRepository = fileRepository;
        this.uploadDir = Paths.get(uploadDirConfig).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            throw new ServiceException("UPLOAD_DIR_ERROR", "Error while creating upload directory", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public File saveFile(Long ownerId, MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String savedFileName = UUID.randomUUID() + "_" + originalFileName;

        Path filePath = uploadDir.resolve(savedFileName);
        try {
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServiceException("FILE_UPLOAD_ERROR", "Error while uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        File file = new File(ownerId, originalFileName, savedFileName, filePath.toString(), multipartFile.getSize(), multipartFile.getContentType());

        return fileRepository.save(file);
    }

    public File getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() ->
            new ServiceException("FILE_NOT_FOUND", "File not found", HttpStatus.NOT_FOUND)
        );
    }

    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() ->
            new ServiceException("FILE_NOT_FOUND", "File not found", HttpStatus.NOT_FOUND)
        );
        try {
            Files.delete(Paths.get(file.getFilePath()));
        } catch (IOException e) {
            throw new ServiceException("FILE_DELETE_ERROR", "Error while deleting file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        fileRepository.delete(file);
    }

}
