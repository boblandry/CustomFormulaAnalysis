package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.entity.Function;
import com.entity.FunctionFromXML;

public class TestFunction {

	public static void main(String[] args) {

		Function f = Function.getInstance();
		ArrayList<FunctionFromXML> function_list = f.getFunction_list();
		for(int i=0;i<function_list.size();i++){
			System.out.println("函数名称:"+function_list.get(i).getFunction_name());
			Map<Integer,String> param_type = function_list.get(i).getParam_type();
			Set<Integer> s = param_type.keySet();
			for(Iterator it = s.iterator();it.hasNext();){
				int type_num = (Integer)it.next();
				System.out.println("参数"+type_num+"的类型："+param_type.get(type_num));
			}
			System.out.println("返回的类型："+function_list.get(i).getReturntype());
			System.out.println("函数的类路径:"+function_list.get(i).getClasspath());
			
		}
		
	}

}
