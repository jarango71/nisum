package com.nisum.evaluation.exception;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = -7907235550005311012L;
	
	public BadRequestException(String message) {
		super(message);
	}
	
    public BadRequestException(String message, Throwable t) {
        super(message, t);
    }
}
