package com.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.log.Log;

public class Function {

	private static final String FUN_PATH = "function.xml";
	private static ArrayList<FunctionFromXML> function_list = new ArrayList<FunctionFromXML>();
	private static Function fun;
	private static final String function_tag = "function";
	private static final String function_name_att = "name";
	private static final String param_type_tag = "paramtype";
	private static final String classpath_tag = "classpath";
	private static final String return_tag = "returntype";
	
	private Function(){
		init();
	}
	
	public static Function getInstance(){
		if(fun ==  null){
			fun = new Function();
		}
		return fun;
	}
	
	private void init(){
		File file = new File(FUN_PATH);
		if(!file.exists()){
			Log.output_error("文件不存在");
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			Element root = doc.getDocumentElement();
			NodeList functions = root.getElementsByTagName(function_tag);
			for(int i =0;i<functions.getLength();i++){
				Element function = (Element) functions.item(i);
				FunctionFromXML ff = new FunctionFromXML();
				ff.setFunction_name(function.getAttribute(function_name_att));
				NodeList params_type = function.getElementsByTagName(param_type_tag);
				Map<Integer,String> types = new HashMap<Integer,String>();
				for(int j = 0;j<params_type.getLength();j++){
					Element param_type = (Element) params_type.item(j);
					String type =  param_type.getTextContent();
					types.put(j, type);
				}
				ff.setParam_type(types);
				NodeList classpath_list = function.getElementsByTagName(classpath_tag);
				Element classpath_e = (Element) classpath_list.item(0);
				String classpath = classpath_e.getTextContent();
				ff.setClasspath(classpath);
				NodeList return_l = function.getElementsByTagName(return_tag);
				Element return_e = (Element) return_l.item(0);
				String returntype = return_e.getTextContent();
				ff.setReturntype(returntype);
				function_list.add(ff);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}

	public static ArrayList<FunctionFromXML> getFunction_list() {
		return function_list;
	}


	
}
