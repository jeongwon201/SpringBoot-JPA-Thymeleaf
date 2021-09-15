package org.hdcd.common.exception;

public class NotMyItemException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotMyItemException(String msg) {
		super(msg);
	}
}
