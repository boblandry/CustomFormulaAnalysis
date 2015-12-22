package com.test;

import java.util.Scanner;
import java.util.Stack;

import com.entity.Operator;
import com.util.Tokenizer4HybridOperation;

public class Test4HybridOperation {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String formula = scan.nextLine();
		Stack result = Tokenizer4HybridOperation.tokenizer(formula);
		while(!result.isEmpty()){
			System.out.print(result.pop().toString());
		}
		
	}

}
