package com.entity;

import java.util.ArrayList;
/**
 * 用来保存 方法名和参数的数据结构
 * @author LichKing
 *
 */
public class MethodEntity {

	private String method_name;
	private ArrayList<String> paramter;
	
	
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public ArrayList<String> getParamter() {
		return paramter;
	}
	public void setParamter(ArrayList<String> paramter) {
		this.paramter = paramter;
	}
	
	
	
}
