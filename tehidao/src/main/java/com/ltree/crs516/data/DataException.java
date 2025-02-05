package com.ltree.crs516.data;

public class DataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataException() {
		// Empty
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}
}
