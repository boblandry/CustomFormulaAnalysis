package com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.entity.Dictionary;
import com.entity.Token;
import com.entity.Tree;
import com.exception.FormulaErrorException;
import com.log.Log;
import com.util.Parser;
import com.util.Tokenizer;

public class TestParser {

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
		Tree tree = new Tree();
		try {
			list = Tokenizer.tokenizer(formula);
			tree = Parser.parser(list);
		} catch (FormulaErrorException e) {
			Log.output_error("¹«Ê½Óï·¨´íÎó");
			e.printStackTrace();
		}
		for(int i=0;i<list.size();i++){
			Token token = list.get(i);
			System.out.println(token.getToken_name()+"\t"+token.getToken_code());
		}
		System.out.println("-----------------------------------------------");
		//System.out.println();
		
		
	}

}
