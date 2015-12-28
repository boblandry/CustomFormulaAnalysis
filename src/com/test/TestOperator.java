package com.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.entity.Operator;

public class TestOperator {

	public static void main(String[] args) {

		/*
		Operator op = Operator.getInstance();
		System.out.println(op.getTest());
		Operator op2 = Operator.getInstance();
		System.out.println(op2.getTest());
		op2.setTest("222222");
		System.out.println(op.getTest());
		System.out.println(op2.getTest());
		*/
		Operator op = Operator.getInstance();
		Map<String,String> test = op.getOperator_map();
		Set<String> set = test.keySet();
		for(Iterator it = set.iterator();it.hasNext();){
			String key = (String)it.next();
			String value = test.get(key);
			System.out.println(key + " " + value);
		}
	}

}
