package com.g2it.realestate.exception;

public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	ErrorStatus errorCode;
    String message;
    Exception originalException;

    public ApplicationException(ErrorStatus errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

	public ApplicationException(ErrorStatus errorCode, String message, Exception exception) {
        this.errorCode = errorCode;
        this.message = message;
        this.originalException = exception;
    }
}
