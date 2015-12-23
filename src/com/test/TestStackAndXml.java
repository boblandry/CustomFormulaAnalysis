package com.test;

import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.util.CustomTokenizer;
import com.util.Tokenizer4HybridOperation;

public class TestStackAndXml {

	public static void main(String[] args) {

		String expression = "(a+b)*c+add[1,2]";
		Stack expression_stack = Tokenizer4HybridOperation.tokenizer(expression);
		Stack temp_stack = new Stack();
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		while(!expression_stack.isEmpty()){
			String element = (String) expression_stack.pop();
			for(int i=0;i<CustomTokenizer.SYMBOL.length-2;i++){
				if(!element.equals(CustomTokenizer.SYMBOL[i])){
					
					temp_stack.push(element);
				}
					
				else{
					Element e = DocumentHelper.createElement(CustomTokenizer.P);
					e.addAttribute(CustomTokenizer.NAME, element);
					
					
					break;
				}
			}
			
		}
		
	}

}
