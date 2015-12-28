package com.entity;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

	private static Dictionary dic;
	private Map<Integer,String> dictionary = new HashMap<Integer,String>();
	
	public static final String LRB = "(";
	public static final String RRB = ")";
	public static final String COMMA = ",";
	public static final String LSB = "[";
	public static final String RSB = "]";
	public static final String OP = "op";
	public static final String FUN = "fun";
	public static final String PARAM = "param";
	
	private Dictionary(){
		initDictionary();
	}
	
	public static Dictionary getInstance(){
		if(dic == null){
			dic = new Dictionary();
		}
		return dic;
	}
	
	public void initDictionary(){
		int key = 0;
		dictionary.put(key, LRB);
		key++;
		dictionary.put(key, RRB);
		key++;
		dictionary.put(key, COMMA);
		key++;
		dictionary.put(key, LSB);
		key++;
		dictionary.put(key, RSB);
		key++;
		dictionary.put(key, OP);
		key++;
		dictionary.put(key, FUN);
		key++;
		dictionary.put(key, PARAM);
		
	}
	
	public Map<Integer,String> getDictionary(){
		return dictionary;
	}
}
