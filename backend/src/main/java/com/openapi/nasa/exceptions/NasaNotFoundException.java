package com.openapi.nasa.exceptions;

public class NasaNotFoundException extends RuntimeException {

	public NasaNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public NasaNotFoundException(String message) {
		super(message);
	}

	public NasaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NasaNotFoundException(Throwable cause) {
		super(cause);
	}
}
