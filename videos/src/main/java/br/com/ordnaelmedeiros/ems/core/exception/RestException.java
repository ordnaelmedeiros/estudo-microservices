package br.com.ordnaelmedeiros.ems.core.exception;

public class RestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RestException(String msg) {
		super(msg);
	}
	
}
