package com.entity;

public class Operator_1{
	public static final String PUL = "+";
	public static final String SUB = "-";
	public static final String MUL = "*";
	public static final String DIV = "/";
	public static final String MOD = "%";
	public static final String LB = "(";
	public static final String RB = ")";
	

	/**
	 * op1>op2 return 1
	 * op1<op2 return -1
	 * op1=op2 return 0
	 * @param op1
	 * @param op2
	 * @return
	 */
	public static int compare(String op1,String op2){
		if(in_low(op1) && !in_low(op2)){
			return -1;
		}else if(!in_low(op1) && in_low(op2)){
			return 1;
		}else{
			return 0;
		}
	}
	
	private static boolean in_low(String op){
		if(op.equals("+") || op.equals("-"))
			return true;
		return false;
	}

}
