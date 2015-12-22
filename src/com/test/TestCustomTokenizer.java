package com.test;

import java.util.Scanner;
import org.dom4j.Document;
import com.util.CustomTokenizer;

public class TestCustomTokenizer {

	public static void main(String[] args) throws Exception {
		/**
		 *  ‰»Î”√¿˝
		 * add(min(5,3),max(2,8),add(1,doubleme(1)))
		 * 
		 */
		Scanner scan = new Scanner(System.in);
		String formula = scan.nextLine();
		Document doc = CustomTokenizer.tokenizer(formula);
		String out = CustomTokenizer.formatXML(doc);
		System.out.println(out);
		
	}

}
