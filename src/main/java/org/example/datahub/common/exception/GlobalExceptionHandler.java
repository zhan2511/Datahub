package org.example.datahub.common.exception;


import org.example.datahub.model.ErrorResponseDTO;
import org.example.datahub.model.ErrorResponseErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleServiceException(ServiceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(
            new ErrorResponseDTO()
               .success(false)
               .error(new ErrorResponseErrorDTO()
                   .code(e.getErrorCode())
                   .message(e.getMessage())
                )
        );
    }
}
