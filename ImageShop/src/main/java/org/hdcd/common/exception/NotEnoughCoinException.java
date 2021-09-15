package org.hdcd.common.exception;

public class NotEnoughCoinException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NotEnoughCoinException(String msg) {
		super(msg);
	}
}
