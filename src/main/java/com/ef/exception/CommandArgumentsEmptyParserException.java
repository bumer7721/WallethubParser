package com.ef.exception;

public class CommandArgumentsEmptyParserException extends RuntimeException {

	public CommandArgumentsEmptyParserException() {
		super();
	}

	public CommandArgumentsEmptyParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandArgumentsEmptyParserException(String message) {
		super(message);
	}

	public CommandArgumentsEmptyParserException(Throwable cause) {
		super(cause);
	}
}
