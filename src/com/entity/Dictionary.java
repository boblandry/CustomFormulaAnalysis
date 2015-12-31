package com.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Dictionary {

	private static Dictionary dic;
	private static Map<Integer,String> dictionary = new HashMap<Integer,String>();
	
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
	
	public static int getCode(String name){
		Set<Integer> set = dictionary.keySet();
		for(Iterator<Integer> it=set.iterator();it.hasNext();){
			int code = it.next();
			String type = (String)dictionary.get(code);
			if(type.equals(name))
				return code;
		}
		return -1;
		
	}
	
	public static String getType(int c){
		Set<Integer> set = dictionary.keySet();
		for(Iterator<Integer> it=set.iterator();it.hasNext();){
			int code = it.next();
			String type = (String)dictionary.get(code);
			if(code == c)
				return type;
		}
		return "";
	}
}
