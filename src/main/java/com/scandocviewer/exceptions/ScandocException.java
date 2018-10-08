package com.scandocviewer.exceptions;

public class ScandocException extends Exception{ 
	 
	private static final long serialVersionUID = 1L;
	
	public ScandocException() {}

    public ScandocException(String message){
       super(message);
    }

}
