package com.scandocviewer.exceptions;

public class ScanDocDataAccessException extends Exception{ 
	 
	private static final long serialVersionUID = 1L;
	
	public ScanDocDataAccessException() {}

   public ScanDocDataAccessException(String message){
      super(message);
   }
}
