package com.util;

public class Priority {

	public static int getPriority(String op){
		if(op.equals("="))
			return 0;
		else if(op.equals("+") || op.equals("-"))
			return 1;
		else if(op.equals("*") || op.equals("/") || op.equals("%"))
			return 2;
		else
			return 10;
	}
	
}
