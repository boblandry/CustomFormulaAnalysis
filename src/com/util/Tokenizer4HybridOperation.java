package com.util;

import java.util.Stack;

import com.entity.Operator;

/**
 * 混合运算的分析器 + - *  / % 
 * @author LichKing
 *
 */
public class Tokenizer4HybridOperation {
	public static Stack tokenizer(Object obj){
		Stack s_operator = new Stack();
		Stack s_operand = new Stack();
		
		String formula = obj.toString();
		String s = "";
		int count = 0;
		for(int i=0;i<formula.length();i++){
			char c = formula.charAt(i);
			if(/*isnum(c)*/ isparam(c) || count>0){
				s = s+c;
				if(String.valueOf(c).equals("["))
					count++;
				if(String.valueOf(c).equals("]"))
					count--;
				if( i+1 ==formula.length() || (!isparam(formula.charAt(i+1)) && count==0)){
					s_operand.push(s);
					s = "";
				}
				continue;
			}else{
				String str = String.valueOf(c);
				if(s_operator.isEmpty()){
					s_operator.push(str);
				}else if(str.equals(Operator.LB)){
					s_operator.push(str);
				}else if(str.equals(Operator.RB)){
					String temp = String.valueOf(s_operator.peek());
					while(!temp.equals(Operator.LB)){
						s_operand.push(temp);
						s_operator.pop();
						temp = (String) s_operator.peek();
					}
					s_operator.pop();
				}else if(s_operator.peek().toString().equals(Operator.LB)){
					s_operator.push(str);
				}else if(Operator.compare(str,s_operator.peek().toString()) == 1){
					s_operator.push(str);
				}else if(Operator.compare(str, s_operator.peek().toString()) != 1){
					String temp = "";
					while(!s_operator.isEmpty() && (Operator.compare(str, s_operator.peek().toString()) != 1 || 
							(s_operator.peek().toString().equals(Operator.LB)
							|| s_operator.peek().toString().equals(Operator.RB)))){
						temp = (String) s_operator.pop();
						s_operand.push(temp);
					}
					s_operator.push(str);
				}
			}
		}
		/*
		while(!s_operator.isEmpty()){
			s_operand.push(s_operator.pop());
		}
		
		return s_operand;
		*/
		while(!s_operand.isEmpty())
			s_operator.push(s_operand.pop());
		return s_operator;
	}
	
	private static boolean isnum(char c){
		if(c>='0' && c<='9')
			return true;
		return false;
	}
	
	private static boolean isparam(char c){
		if((c>='0' && c<='9') || (c>='a' && c<='z') 
				|| (c>='A' && c<='Z') || c=='[' || c==']' || c=='_' || c==','){
			return true;
		}
		return false;
	}
}
