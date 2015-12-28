package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.entity.Dictionary;
import com.entity.Token;
import com.exception.FormulaGrammarException;
import com.log.Log;
import com.util.Tokenizer;

public class TestTokenizer {

	/**
	 * add[min[5,3],max[2,8],add[1,doubleme[1]]]
	 * (a+b)*c+add[1,2]
	 * add[a+b,2]
	 * add[a,sub[2+b,3]]
	 * add[a,sub[2+b,3]+2]  ******
	 * add[a+sub[2,b],1]
	 * sub[2+b,4]+2
	 * sub[2+4,1]+div[2,a]*c
	 * mul[a,b]+find[t,g,b]%b
	 * (mul[a,b]+find[t,g,b]%b)*fd
	 * (mul[a,(c+b)/add[l,k]]+find[t,g,b]%b)*fd
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String formula = scan.nextLine();
		Dictionary dic = Dictionary.getInstance();
		Map<Integer,String> dictionary = dic.getDictionary();
		Set<Integer> set = dictionary.keySet();
		for(Iterator it=set.iterator();it.hasNext();){
			int code = (Integer)it.next();
			System.out.println(dictionary.get(code)+"\t"+code);
		}
		System.out.println("---------------------------------------------");
		ArrayList<Token> list = new ArrayList<Token>();
		try {
			list = Tokenizer.tokenizer(formula);
		} catch (FormulaGrammarException e) {
			Log.output_error("¹«Ê½Óï·¨´íÎó");
			e.printStackTrace();
		}
		for(int i=0;i<list.size();i++){
			Token token = list.get(i);
			System.out.println(token.getToken_name()+"\t"+token.getToken_code());
		}
	}
	
}
