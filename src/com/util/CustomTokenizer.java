package com.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.entity.MethodEntity;

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
		MethodEntity me = getNameAndParam(formula);
		generatexml(me,root);	
		
		return doc;
	}
	
	/**
	 * 判断字符串自否为公式  （默认公式没有书写错误）
	 * @param formula
	 * @return
	 */
	private static boolean isformula(String formula){
		
		String f = formula.trim();
		char last = f.charAt(f.length()-1);
		char first = f.charAt(0);
		int lb_index = f.indexOf("(");
		if(last==')' && check_first(first) && lb_index>0 && lb_index<f.length()-1)
			return true;
		return false;
	}
	
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
		for(int i=0;i<formula.indexOf('(');i++){
			char c = formula.charAt(i);
			if(c != '(')
				sb.append(c);
		}
		String param = "";
		int count=0;
		for(int i=formula.indexOf('(')+1;i<formula.length();i++){
			String s  = String.valueOf(formula.charAt(i));
			if(s.equals("(")){
				count++;
				param += s;
				continue;
			}else if(count>0 && s.equals(")")){
				count--;
				param += s;
				continue;
			}else if (count>0 && s.equals(",")){
				param += s;
			}
			if(!s.equals(",") && !s.equals(")")){
				param += s;
			}else if ((s.equals(",") || s.equals(")")) && count==0){
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
	 * 递归生成xml
	 * @param me
	 * @param parent_node
	 */
	private static void generatexml(MethodEntity me , Element parent_node){
		String name = me.getMethod_name();
		ArrayList<String> param = me.getParamter();
		Element m = parent_node.addElement(M);
		m.addAttribute(NAME, name);
		for (String p : param){
			Element e_p = m.addElement(P);
			if(isformula(p)){
				MethodEntity child_me = getNameAndParam(p);
				generatexml(child_me,e_p);
			}else{
				e_p.addText(p);
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
	public static final String M = "method";
	public static final String NAME = "name";
	public static final String P = "param";
}
