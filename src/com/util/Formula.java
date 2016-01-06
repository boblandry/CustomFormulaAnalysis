package com.util;

import java.util.ArrayList;

import com.entity.Token;
import com.entity.Tree;
import com.exception.FormulaErrorException;

public class Formula {

	public static ArrayList<Tree> generateFormulaTrees(String formulas) throws FormulaErrorException{
		ArrayList<Tree> tree_list = new ArrayList<Tree>();
		
		if(formulas.contains(";")){
			String[] formula_list = formulas.split(";");
			for(String formula : formula_list){
				ArrayList<Token> token_list = Tokenizer.tokenizer(formula);
				Tree tree = Parser.parser(token_list);
				tree_list.add(tree);
			}
		}else{
			ArrayList<Token> token_list = Tokenizer.tokenizer(formulas);
			Tree tree = Parser.parser(token_list);
			tree_list.add(tree);
		}
		
		return tree_list;
	}
	
}
