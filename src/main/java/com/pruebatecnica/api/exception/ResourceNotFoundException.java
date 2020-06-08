package com.pruebatecnica.api.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -1318202414576960480L;

	public ResourceNotFoundException(String message){
    	super(message);
    }
}
