package com.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.log.Log;

public class Operator {

	private static final String OP_PATH = "operator";
	private static Map<String,String> operator_map = null;
	//private static String test = "";
	private static Operator op;
	
	private Operator(){
		init();
	}
	
	public static Operator getInstance(){
		if(op ==  null){
			op = new Operator();
			//test = "111111";
		}
		return op;
	}
	
	private void init(){
		File file = new File(OP_PATH);
		if(!file.exists()){
			Log.output_error("文件不存在");
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String temp = null;
			operator_map = new HashMap<String,String>();
			while ((temp = reader.readLine()) != null){
				String result[] = temp.split(";");
				operator_map.put(result[0], result[1]);
			}
		} catch (IOException e) {
			//Log.output_error("FileNotFoundException");
			e.printStackTrace();
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}
	/*
	public static String getTest() {
		return test;
	}

	public static void setTest(String test) {
		Operator.test = test;
	}
	*/

	public static Map<String, String> getOperator_map() {
		return operator_map;
	}
	 
	
	
}
