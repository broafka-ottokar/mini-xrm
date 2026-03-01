package com.example.minixrm.backend.web.config;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.facade.util.ErrorCode;
import com.example.minixrm.backend.web.openapi.v1.model.ErrorResponseView;

@ControllerAdvice
public class ErrorHandlerConfig {
	
	private final Logger logger = LoggerFactory.getLogger(ErrorHandlerConfig.class);
	
	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<ErrorResponseView> handleOptimisticLockingFailure(OptimisticLockingFailureException ex) {
		logger.info("Optimistic locking failure", ex);
		ErrorCode errorCode = ErrorCode.CONCURRENT_MODIFICATION;
		ErrorResponseView ErrorResponseView = createErrorResponseView(errorCode, "Optimistic locking failure", ex);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseView);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponseView> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		logger.info("Bad request JSON", ex);
		ErrorCode errorCode = ErrorCode.BAD_REQUEST_JSON;
		ErrorResponseView ErrorResponseView = createErrorResponseView(errorCode, "Bad request JSON", ex);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseView);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseView> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		logger.info("Validation failed", ex);
		ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
		ErrorResponseView ErrorResponseView = createErrorResponseView(errorCode, "Validation failed", ex);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseView);
	}
	
	@ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
	public ResponseEntity<ErrorResponseView> handleJakartaConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {
		logger.info("Constraint violation", ex);
		ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
		ErrorResponseView ErrorResponseView = createErrorResponseView(errorCode, "Constraint violation", ex);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseView);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponseView> handleConstraintViolationException(ConstraintViolationException ex) {
		logger.info("Constraint violation", ex);
		ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
		ErrorResponseView ErrorResponseView = createErrorResponseView(errorCode, "Constraint violation", ex);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseView);
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorResponseView> handleApplicationException(ApplicationException ex) {
		ErrorCode errorCode = ex.getErrorCode();
		if (errorCode == ErrorCode.INTERNAL_SERVER_ERROR) {
		logger.error("Application exception", ex);
		} else {
		logger.info("Application exception", ex);
		}
		ErrorResponseView ErrorResponseView = createErrorResponseView(errorCode, ex.getPublicMessage(), null);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(ErrorResponseView);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponseView handleGenericException(Exception ex) {
		logger.error("Internal server error", ex);
		ErrorResponseView ErrorResponseView = createErrorResponseView(ErrorCode.INTERNAL_SERVER_ERROR, "Internal server error", ex);
		return ErrorResponseView;
	}
	
	private ErrorResponseView createErrorResponseView(ErrorCode internalServerError, String string, Exception ex) {
		String details = (ex != null) ? ex.getMessage() : null;
		return new ErrorResponseView(internalServerError.name(), string, details);
	}

}