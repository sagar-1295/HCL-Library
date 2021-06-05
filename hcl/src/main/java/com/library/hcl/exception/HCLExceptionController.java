package com.library.hcl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HCLExceptionController {
	
	@ExceptionHandler(value = MemberNotFoundException.class)
	public ResponseEntity<Object> exception(MemberNotFoundException exception) {
		return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<Object> exceptionBook(BookNotFoundException exception) {
		return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
	}

}
