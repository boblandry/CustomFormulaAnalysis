package com.entity;

import java.util.Map;


public class FunctionFromXML {

	private String function_name;
	private Map<Integer,String> param_type;
	private String returntype;
	private String classpath;
	
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	public Map<Integer, String> getParam_type() {
		return param_type;
	}
	public void setParam_type(Map<Integer, String> param_type) {
		this.param_type = param_type;
	}
	public String getClasspath() {
		return classpath;
	}
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
	public String getReturntype() {
		return returntype;
	}
	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}
	
	
	
}
