package com.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.entity.Dictionary;
import com.entity.Operator;
import com.entity.Token;
import com.exception.FormulaGrammarException;
import com.log.Log;

public class Tokenizer {

	public static ArrayList<Token> tokenizer(String formula) throws FormulaGrammarException{
		formula = formula.trim();
		ArrayList<Token> token_list = new ArrayList<Token>();
		int state = 0;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<formula.length();i++){
			String current = String.valueOf(formula.charAt(i));
			if(current.equals(" "))
				continue;
			switch(state){
				case 0:
					if(current.equals(Dictionary.LRB)){
						Token token = setToken(current,Dictionary.LRB);
						token_list.add(token);
					}else if(current.equals(Dictionary.RRB)){
						Token token = setToken(current,Dictionary.RRB);
						token_list.add(token);
					}else if(current.equals(Dictionary.COMMA)){
						Token token = setToken(current,Dictionary.COMMA);
						token_list.add(token);
					}else if(current.equals(Dictionary.LSB)){
						Token token = setToken(current,Dictionary.LSB);
						token_list.add(token);
					}else if(current.equals(Dictionary.RSB)){
						Token token = setToken(current,Dictionary.RSB);
						token_list.add(token);
					}else if(isInOperator(current)){
						Token token = setToken(current,Dictionary.OP);
						token_list.add(token);
					}else if(isInChar(current)){
						state = 1;
						sb.append(current);
					}else{
						Log.output_error("当前字符:"+current);
						Log.output_error("上一个字符:"+String.valueOf(formula.charAt(i-1)));
						throw new FormulaGrammarException();
					}
					break;
				case 1:
					if(isInChar(current)){
						sb.append(current);
						if(i == formula.length()-1){
							Token token = setToken(sb.toString(), Dictionary.PARAM);
							token_list.add(token);
							i--;
							state = 0;
							sb.setLength(0);
						}
					}else if(current.equals(Dictionary.LSB)){
						Token token = setToken(sb.toString(), Dictionary.FUN);
						token_list.add(token);
						i--;
						state = 0;
						sb.setLength(0);
					}else if(current.equals(Dictionary.COMMA) ||
							current.equals(Dictionary.RSB) ||
							current.equals(Dictionary.RRB) ||
							isInOperator(current)){
						Token token = setToken(sb.toString(), Dictionary.PARAM);
						token_list.add(token);
						i--;
						state = 0;
						sb.setLength(0);
					}else{
						Log.output_error("当前字符:"+current);
						Log.output_error("上一个字符:"+String.valueOf(formula.charAt(i-1)));
						throw new FormulaGrammarException();
					}
					break;
				default:
					throw new FormulaGrammarException();
			}	
		}
		
		return token_list;
	}
	
	private static int getCode(String str){
		Dictionary dic = Dictionary.getInstance();
		Map<Integer,String> dictionary = dic.getDictionary();
		Set<Integer> set = dictionary.keySet();
		for(Iterator it=set.iterator();it.hasNext();){
			int key = (Integer)it.next();
			if(str.equals(dictionary.get(key))){
				return key;
			}
		}
		return -1;
	}
	
	private static Token setToken(String current,String str){
		int code = getCode(str);
		Token token = new Token();
		token.setToken_name(current);
		token.setToken_code(code);
		return token;
	}
	
	private static boolean isInChar(String current){
		char c = current.charAt(0);
		if((c>='a' && c<='z') ||
				(c>='A' && c<='Z') || 
				(c>='0' && c<='9') || c=='_')
			return true;
		return false;
	}
	
	private static boolean isInOperator(String current){
		Operator op = Operator.getInstance();
		Map<String,String> operator_map = op.getOperator_map();
		Set<String> set = operator_map.keySet();
		for(Iterator it=set.iterator();it.hasNext();){
			String key = (String)it.next();
			String value = operator_map.get(key);
			if(current.equals(value)){
				return true;
			}
		}
		return false;
	}
}
