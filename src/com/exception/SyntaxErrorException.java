package com.exception;

import com.log.Log;

public class SyntaxErrorException extends FormulaErrorException {

	public SyntaxErrorException(){
		super();
		Log.output_error("Óï·¨´íÎó");
	}
	
}
