package org.example.datahub.file;


import org.example.datahub.api.FilesApi;
import org.example.datahub.auth.JwtFilter;
import org.example.datahub.common.exception.ServiceException;
import org.example.datahub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class FileController implements FilesApi {
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(
        FileService fileService,
        UserService userService
    ) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<Resource> getFile(Long fileId) {
        // check permissions
        Long currentUserId = JwtFilter.getCurrentUserId();
        String currentUserRole = userService.getUserById(currentUserId).getRole();
        if (!"Administrator".equals(currentUserRole) &&
            !currentUserId.equals(fileService.getFileById(fileId).getOwnerId()) &&
            !"Assistant".equals(currentUserRole)) {
            throw new ServiceException(
                "PERMISSION_DENIED",
                "You do not have permission to access this file",
                HttpStatus.FORBIDDEN
            );
        }

        File file = fileService.getFileById(fileId);
        Resource resource = new FileSystemResource(file.getFilePath());
        return ResponseEntity.ok()
           .header("Content-Disposition", "attachment; filename=\"" + file.getOriginalFileName() + "\"")
           .body(resource);
    }
}
