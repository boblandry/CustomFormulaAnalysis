package com.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Stack;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.entity.MethodEntity;
import com.entity.Operator;

public class CustomTokenizer {
	/**
	 *  test xml
	public static void main(String[] args) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		Element method = root.addElement("method");
		method.addAttribute("id", "add");
		method.addText("test");
		String out = doc.asXML();
		System.out.println(out);
	}
	*/
	public static Document tokenizer(Object obj){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		String formula =  obj.toString();
		if(isoperation(formula)){
			Stack operation_stack = Tokenizer4HybridOperation.tokenizer(formula);
			generate_expression_xml(operation_stack, root);
		}
		if(isformula(formula)){
			MethodEntity me = getNameAndParam(formula);
			generatexml(me,root);	
		}
		return doc;
	}
	
	/**
	 * 判断字符串自否为公式  （默认公式没有书写错误）
	 * @param formula
	 * @return
	 */
	private static boolean isformula(String formula){
		
		String f = formula.trim();
		if(f.equals(""))
			return false;
		char last = f.charAt(f.length()-1);
		char first = f.charAt(0);
		int lb_index = f.indexOf("[");
		boolean is_pairing = ispairing(formula);
		if(last==']' && check_first(first) && lb_index>0 
				&& lb_index<f.length()-1 && is_pairing)
			return true;
		return false;
	}
	
	/**
	 * 方法名首字母是a_z 或者A_Z 或者_
	 * @param c
	 * @return
	 */
	private static boolean check_first(char c){
		if((c>='a' && c<='z') || (c>='A' && c<='Z') || c=='_')
			return true;
		return false;
	}
	
	/**
	 * 从formula中拆除方法名和参数（就拆一次）
	 * @param formula
	 * @return
	 */
	private static MethodEntity getNameAndParam(String formula){
		MethodEntity me = new MethodEntity();
		StringBuffer sb = new StringBuffer();
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<formula.indexOf('[');i++){
			char c = formula.charAt(i);
			if(c != '[')
				sb.append(c);
		}
		String param = "";
		int count=0;
		for(int i=formula.indexOf('[')+1;i<formula.length();i++){
			String s  = String.valueOf(formula.charAt(i));
			if(s.equals("[")){
				count++;
				param += s;
				continue;
			}else if(count>0 && s.equals("]")){
				count--;
				param += s;
				continue;
			}else if (count>0 && s.equals(",")){
				param += s;
			}
			if(!s.equals(",") && !s.equals("]")){
				param += s;
			}else if ((s.equals(",") || s.equals("]")) && count==0){
				list.add(param);
				param = "";
			}
		}
		String methodname = sb.toString();
		me.setMethod_name(methodname);
		me.setParamter(list);
		return me;
	}
	
	/**
	 * 递归生成xml  如果这层是先执行方法 则调用此方法
	 * @param me
	 * @param parent_node
	 */
	private static void generatexml(MethodEntity me , Element parent_node){
		String name = me.getMethod_name();
		ArrayList<String> param = me.getParamter();
		Element m = parent_node.addElement(M);
		m.addAttribute(NAME, name);
		for (String p : param){
			if(p.equals(""))
				continue;
			Element e_p = m.addElement(P);
			if(ispureparam(p)){
				e_p.addText(p);
			}else if(isoperation(p)){
				Stack operation_stack = Tokenizer4HybridOperation.tokenizer(p);
				generate_expression_xml(operation_stack,e_p);
			}else if(isformula(p)){
				MethodEntity child_me = getNameAndParam(p);
				generatexml(child_me,e_p);
			}
		}
	}
	
	/**
	 * 格式化输出XML格式
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static String formatXML(Document doc) throws Exception {  
		SAXReader reader = new SAXReader();  
        Document document = doc;  
        String requestXML = null;  
        XMLWriter writer = null;  
        if (document != null) {  
        	try {  
        		StringWriter stringWriter = new StringWriter();  
        		OutputFormat format = new OutputFormat(" ", true);  
        		writer = new XMLWriter(stringWriter, format);  
        		writer.write(document);  
        		writer.flush();  
        		requestXML = stringWriter.getBuffer().toString();  
        	} finally {  
        		if (writer != null) {  
        			try {  
        				writer.close();  
        			} catch (IOException e) {  
        			}  
        		}  
        	}  
        }  
        return requestXML;  
    }  
	
	/**
	 * 检查式子第一个出现的括号是否与最后一个括号匹配
	 * @param formula
	 * @return
	 */
	private static boolean ispairing(String formula){
		//count 信号量 用来记录括号是否匹配
		int count=0;
		//flag 只有检测到第一个左括号后 才开始检查count的值
		boolean flag=false;
		for(int i=0;i<formula.length();i++){
			String s = String.valueOf(formula.charAt(i));
			if(s.equals("[")){
				count++;
				flag = true;
			}
			else if(s.equals("]"))
				count--;
			if(count<=0 && i<formula.length()-1 && flag)
				return false;
		}
		if(count!=0)
			return false;
		return true;
	}
	
	/**
	 * 检查是否为纯参数  不需要再分解
	 * @param str
	 * @return
	 */
	private static boolean ispureparam(String str){
		for(int i=0;i<SYMBOL.length;i++){
			for(int j=0;j<str.length();j++){
				String s = String.valueOf(str.charAt(j));
				if(s.equals(SYMBOL[i]))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 检查是否为运算而不是方法
	 * @param str
	 * @return
	 */
	private static boolean isoperation(String str){
		int count = 0;
		for(int i=0;i<str.length();i++){
			String s = String.valueOf(str.charAt(i));
			if(s.equals(SYMBOL[SYMBOL.length-2] ))
				count++;
			if(s.equals(SYMBOL[SYMBOL.length-1] ))
				count--;
			if(count==0){
				for(int j=0;j<SYMBOL.length-2;j++){
					if(s.equals(SYMBOL[j]))
						return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 如果这一层先执行运算 则先调用这个方法
	 * @param operation_stack
	 * @param parent_element
	 */
	private static void generate_expression_xml(Stack operation_stack,Element parent_element){
		Stack temp_stack = new Stack();
		while(!operation_stack.isEmpty()){
			boolean is_operator = false;
			String element = (String) operation_stack.pop();
			for(int i=0;i<SYMBOL.length-2;i++){
				if(element.equals(SYMBOL[i])){
					is_operator = true;
					if(operation_stack.size()==0){
						Element m = DocumentHelper.createElement(M);
						m.addAttribute(NAME, element);
						m.add((Element)temp_stack.pop());
						m.add((Element)temp_stack.pop());
						parent_element.add(m);
						//temp_stack.push(parent_element);
					}else{
						Element p = DocumentHelper.createElement(P);
						Element m = DocumentHelper.createElement(M);
						m.addAttribute(NAME, element);
						m.add((Element)temp_stack.pop());
						m.add((Element)temp_stack.pop());
						p.add(m);
						temp_stack.push(p);
					}
					break;
				}
			}
			if(!is_operator){
				Element e = DocumentHelper.createElement(P);
				if(ispureparam(element)){
					e.addText(element);
				}else if(isoperation(element)){
					Stack child_operation_stack = Tokenizer4HybridOperation.tokenizer(element);
					generate_expression_xml(child_operation_stack,e);
				}else if(isformula(element)){
					MethodEntity child_me = getNameAndParam(element);
					generatexml(child_me,e);
				}
				temp_stack.push(e);
			}
		}
		//Element temp = (Element) temp_stack.pop();
		//parent_element.add(temp);
	}
	
	
	
	public static final String[] SYMBOL = {Operator.DIV,Operator.LB,Operator.MOD,
			Operator.MUL,Operator.PUL,Operator.RB,Operator.SUB,"[","]"};
	public static final String M = "method";
	public static final String NAME = "name";
	public static final String P = "param";
	/*
	public static void main(String[] args) throws Exception {

		String expression = "(a+b)*c+add[1,2]";
		Stack expression_stack = Tokenizer4HybridOperation.tokenizer(expression);
		Stack temp_stack = new Stack();
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("root");
		while(!expression_stack.isEmpty()){
			String element = (String) expression_stack.pop();
			for(int i=0;i<CustomTokenizer.SYMBOL.length-2;i++){
				if(element.equals(CustomTokenizer.SYMBOL[i])){
					Element p = DocumentHelper.createElement(CustomTokenizer.P);
					Element m = DocumentHelper.createElement(CustomTokenizer.M);
					m.addAttribute(CustomTokenizer.NAME, element);
					//String param1 = (String) temp_stack.pop();
					//String param2 = (String) temp_stack.pop();
					//Element p1 = DocumentHelper.createElement(CustomTokenizer.P);
					//Element p2 = DocumentHelper.createElement(CustomTokenizer.P);
					
					//p1.addText(param1);
					//p2.addText(param2);
					m.add((Element)temp_stack.pop());
					m.add((Element)temp_stack.pop());
					p.add(m);
					temp_stack.push(p);
					break;
				}
				else{
					if(i == SYMBOL.length-3){
						//if(ispureparam(element)){
							Element e = DocumentHelper.createElement(P);
							e.addText(element);
							temp_stack.push(e);
						//}
						
						//temp_stack.push(element);
					}
				}
			}
		}
		Element t = (Element) temp_stack.pop();
		root.add(t);
		String out = CustomTokenizer.formatXML(doc);
		System.out.println(out);
		
	}
	*/
}
