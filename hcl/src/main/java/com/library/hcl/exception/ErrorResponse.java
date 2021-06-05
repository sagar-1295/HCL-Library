package com.library.hcl.exception;

public class ErrorResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public ErrorResponse(String message) {
		super();
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
