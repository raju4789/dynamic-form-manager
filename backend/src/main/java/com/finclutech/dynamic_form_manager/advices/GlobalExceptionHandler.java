package com.finclutech.dynamic_form_manager.advices;

import com.finclutech.dynamic_form_manager.dtos.common.CommonApiResponse;
import com.finclutech.dynamic_form_manager.dtos.common.ErrorDetails;
import com.finclutech.dynamic_form_manager.exceptions.InvalidRequestException;
import com.finclutech.dynamic_form_manager.exceptions.RecordAlreadyExistsException;
import com.finclutech.dynamic_form_manager.exceptions.RecordNotFoundException;
import com.finclutech.dynamic_form_manager.exceptions.UserUnAuthorizedException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * GlobalExceptionHandler is a centralized exception handling class for the application.
 * It intercepts and handles various exceptions thrown by the application and provides
 * appropriate HTTP responses with detailed error messages.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles InvalidRequestException and returns a BAD_REQUEST response.
     *
     * @param ex the InvalidRequestException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<CommonApiResponse<String>> handleInvalidRequestException(InvalidRequestException ex) {
        log.error("InvalidRequestException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonApiResponse);
    }

    /**
     * Handles SignatureException and returns an UNAUTHORIZED response.
     *
     * @param ex the SignatureException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<CommonApiResponse<String>> handleSignatureException(SignatureException ex) {
        log.error("SignatureException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonApiResponse);
    }

    /**
     * Handles RecordNotFoundException and returns a BAD_REQUEST response.
     *
     * @param ex the RecordNotFoundException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<CommonApiResponse<String>> handleRecordNotFoundException(RecordNotFoundException ex) {
        log.error("RecordNotFoundException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonApiResponse);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a BAD_REQUEST response.
     *
     * @param ex the MethodArgumentNotValidException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException occurred: {}", ex.getMessage(), ex);
        List<String> fieldErrorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(fieldErrorMessages.toString())
                .build();

        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();

        return ResponseEntity.badRequest().body(commonApiResponse);
    }

    /**
     * Handles UserUnAuthorizedException and returns a FORBIDDEN response.
     *
     * @param ex the UserUnAuthorizedException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(UserUnAuthorizedException.class)
    public ResponseEntity<CommonApiResponse<String>> handleUnAuthorisedException(UserUnAuthorizedException ex) {
        log.error("UserUnAuthorizedException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonApiResponse);
    }

    /**
     * Handles AccessDeniedException and returns a FORBIDDEN response.
     *
     * @param ex the AccessDeniedException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(commonApiResponse);
    }

    /**
     * Handles AuthenticationException and returns an UNAUTHORIZED response.
     *
     * @param ex the AuthenticationException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonApiResponse<String>> handleUnAuthenticatedException(AuthenticationException ex) {
        log.error("AuthenticationException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonApiResponse);
    }

    /**
     * Handles RecordAlreadyExistsException and returns a BAD_REQUEST response.
     *
     * @param ex the RecordAlreadyExistsException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<CommonApiResponse<String>> handleRecordAlreadyExistsException(RecordAlreadyExistsException ex) {
        log.error("RecordAlreadyExistsException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonApiResponse);
    }

    /**
     * Handles RuntimeException and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param ex the RuntimeException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonApiResponse);
    }

    /**
     * Handles generic Exception and returns an INTERNAL_SERVER_ERROR response.
     *
     * @param ex the Exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<String>> handleDefaultException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .build();
        CommonApiResponse<String> commonApiResponse = CommonApiResponse.<String>builder()
                .errorDetails(errorDetails)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(commonApiResponse);
    }
}