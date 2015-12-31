package com.util;

import java.util.ArrayList;

import com.entity.Dictionary;
import com.entity.Token;
import com.entity.Tree;
import com.exception.FormulaErrorException;
import com.exception.SyntaxErrorException;

public class Parser {

	public static Tree parser(ArrayList<Token> token_list) throws FormulaErrorException{
		//Tree root = new Tree();
		//将所有的token都转化为tree类型
		ArrayList<Tree> tree_list = initTreeList(token_list);
		
		Tree now_tree = tree_list.get(0);
		for(int i=1;i<tree_list.size();i++){
			Tree next_tree = tree_list.get(i);
			if(now_tree.getType().equals(Dictionary.FUN)){
				if(!next_tree.getName().equals(Dictionary.LSB))
					throw new SyntaxErrorException();
				i = getFunTree(tree_list,i)+1;
				
			}
		}
		return now_tree;
	}
	
	private static int getFunTree(ArrayList<Tree> tree_list,int next_pos){
		Tree now_tree = tree_list.get(next_pos-1);
		ArrayList<Tree> child_tree = new ArrayList<Tree>();
		int semaphore = 0,count_i=0;
		for(int i=next_pos;i<tree_list.size();i++){
			Tree tree = tree_list.get(i);
			Tree next_tree = new Tree();
			if(i != tree_list.size()-1){
				next_tree = tree_list.get(i+1);
			}
			//String value = tree.getName();
			//String next_value = next_tree.getName();
			String type = tree.getType();
			String next_type = next_tree.getType();
			if(type.equals(Dictionary.LSB))
				semaphore++;
			else if(type.equals(Dictionary.RSB))
				semaphore--;
			if(semaphore == 0){
				count_i = i;
				break;
			}
				
			
			if(type.equals(Dictionary.PARAM) && (next_type.equals(Dictionary.COMMA) || 
					next_type.equals(Dictionary.RSB))){
				child_tree.add(tree);
			}else if(type.equals(Dictionary.FUN)){
				i = getFunTree(tree_list,i+1);
				child_tree.add(tree);
			}// not finished
			
		}
		now_tree.setChild_tree(child_tree);
		if(semaphore == 0)
			return count_i;
		return tree_list.size();
	}
	
	private static ArrayList<Tree> initTreeList(ArrayList<Token> token_list) throws FormulaErrorException{
		ArrayList<Tree> tree_list = new ArrayList<Tree>();
		for(int i=0;i<token_list.size();i++){
			Tree tree = new Tree();
			Token token = token_list.get(i);
			tree.setName(token.getToken_name());
			int code = token.getToken_code();
			String type = Dictionary.getType(code);
			if(type == null || type.equals(""))
				throw new FormulaErrorException();
			tree.setType(type);
			tree_list.add(tree);
		}
		return tree_list;
	}
}
