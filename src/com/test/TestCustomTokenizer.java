package com.test;

import java.util.Scanner;
import org.dom4j.Document;

import com.util.CustomTokenizer;
import com.util.OutputXML;


public class TestCustomTokenizer {

	public static void main(String[] args) throws Exception {
		/**
		 *  ‰»Î”√¿˝
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
		 */
		Scanner scan = new Scanner(System.in);
		String formula = scan.nextLine();
		Document doc = CustomTokenizer.tokenizer(formula);
		String out = CustomTokenizer.formatXML(doc);
		System.out.println(out);
		//OutputXML.output(out);
		
	}

}
