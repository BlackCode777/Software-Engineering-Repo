package br.com.archsoft.constructor.domain.exception;

public class DomainValidationException extends RuntimeException {

	public DomainValidationException(String message) {
		super(message);
	}
}
