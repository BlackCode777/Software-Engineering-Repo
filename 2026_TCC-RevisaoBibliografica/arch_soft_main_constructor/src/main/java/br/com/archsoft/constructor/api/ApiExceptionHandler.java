package br.com.archsoft.constructor.api;

import br.com.archsoft.constructor.api.dto.ApiErrorResponse;
import br.com.archsoft.constructor.domain.exception.DomainValidationException;
import br.com.archsoft.constructor.domain.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
		List<String> messages = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.toList();
		return error(HttpStatus.BAD_REQUEST, messages);
	}

	@ExceptionHandler(DomainValidationException.class)
	public ResponseEntity<ApiErrorResponse> handleDomainValidation(DomainValidationException ex) {
		return error(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(OrderNotFoundException ex) {
		return error(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
	}

	private ResponseEntity<ApiErrorResponse> error(HttpStatus status, List<String> messages) {
		return ResponseEntity.status(status)
				.body(new ApiErrorResponse(OffsetDateTime.now(), status.value(), status.getReasonPhrase(), messages));
	}
}
