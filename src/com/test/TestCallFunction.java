package com.test;

import java.lang.reflect.Method;

public class TestCallFunction {

	public static void main(String[] args) {

		int a = 4;
		int b = 2;
		try {
			Class c = Class.forName("com.function.Add");
			Class param_c = Class.forName("java.lang.Integer");
			Method method = c.getMethod("add", param_c,param_c);
			Object result = method.invoke(c.newInstance(), a,b);
			System.out.println(result.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
