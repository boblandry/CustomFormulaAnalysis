package com.function;

public class Max {
	public static int max(int a,int b,int c){
		int t;
		if(a>b)
			t = a;
		else
			t = b;
		if(t>c)
			return t;
		else
			return c;
	}
}
