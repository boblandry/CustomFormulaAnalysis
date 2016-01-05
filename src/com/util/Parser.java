package com.util;

import java.util.ArrayList;
import java.util.Stack;

import com.entity.Dictionary;
import com.entity.Token;
import com.entity.Tree;
import com.entity.TreeAndPos;
import com.entity.TreeListAndPos;
import com.exception.FormulaErrorException;
import com.exception.SyntaxErrorException;

public class Parser {

	public static Tree parser(ArrayList<Token> token_list) throws FormulaErrorException{
		//Tree root = new Tree();
		//将所有的token都转化为tree类型
		ArrayList<Tree> tree_list = initTreeList(token_list);
		
		ArrayList<Tree> HCParamList = getHCParamList(tree_list);
		Tree result = generateHCExpression(HCParamList);
		return result;
	}
	
	//传入表达式参数 生成表达式树
	private static Tree generateHCExpression(ArrayList<Tree> HCParamList) throws SyntaxErrorException{
		
		Stack<Tree> s_q = HCTSort.sort(HCParamList);
		Stack<Tree> s_r = new Stack<Tree>();
		while(!s_q.isEmpty()){
			Tree temp_tree = s_q.pop();
			if(temp_tree.getChild_tree_list()  == null && !temp_tree.getType().equals(Dictionary.PARAM)){
				Tree op1 = s_r.pop();
				Tree op2 = s_r.pop();
				ArrayList<Tree> child_tree_list = new ArrayList<Tree>();
				child_tree_list.add(op1);
				child_tree_list.add(op2);
				temp_tree.setChild_tree(child_tree_list);
				s_r.push(temp_tree);
			}else{
				s_r.push(temp_tree);
			}
		}
		/*
		while(s.size() != 1){
			try{
				Tree t1 = s.pop();
				Tree t2 = s.pop();
				Tree t3 = s.pop();
				ArrayList<Tree> child_tree_list = new ArrayList<Tree>();
				child_tree_list.add(t1);
				child_tree_list.add(t2);
				t3.setChild_tree(child_tree_list);
				s.push(t3);
			}catch(Exception e){
				throw new SyntaxErrorException();
			}
		}
		*/
		Tree parent = s_r.pop();
		return parent;
	}
	
	//获取表达式参数
	private static ArrayList<Tree> getHCParamList(ArrayList<Tree> tree_list) throws SyntaxErrorException{
		ArrayList<Tree> HCParamList = new ArrayList<Tree>();
		
		for(int i=0;i<tree_list.size();i++){
			Tree now_tree = tree_list.get(i);
			Tree next_tree;
			if(i+1<tree_list.size())
				next_tree = tree_list.get(i+1);
			else
				next_tree = new Tree();
			if(now_tree.getType().equals(Dictionary.FUN)){
				if(!next_tree.getName().equals(Dictionary.LSB))
					throw new SyntaxErrorException();
				TreeAndPos tap = getFunTree(tree_list,i+1);
				i = tap.getPos();
				HCParamList.add(tap.getTree());
			}else if(now_tree.getType().equals(Dictionary.OP)
					|| now_tree.getType().equals(Dictionary.LRB)
					|| now_tree.getType().equals(Dictionary.RRB)
					|| now_tree.getType().equals(Dictionary.PARAM)){
				HCParamList.add(now_tree);
			}else{
				throw new SyntaxErrorException();
			}
		}
		
		return HCParamList;
	}
	
	//生成方法的树
	private static TreeAndPos getFunTree(ArrayList<Tree> tree_list,int next_pos) throws SyntaxErrorException{
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
			if(type.equals(Dictionary.LSB)){
				semaphore++;
				continue;
			}
			else if(type.equals(Dictionary.RSB)){
				semaphore--;
			}
			if(semaphore == 0){
				count_i = i;
				break;
			}
				
			
			if(type.equals(Dictionary.PARAM) && (next_type.equals(Dictionary.COMMA) || 
					next_type.equals(Dictionary.RSB))){
				child_tree.add(tree);
			}else if(type.equals(Dictionary.FUN)){
				/*
				TreeAndPos tap_temp = getFunTree(tree_list,i+1);
				child_tree.add(tap_temp.getTree());
				i = tap_temp.getPos();
				*/
				TreeListAndPos tlap = findExpressionTreeList(tree_list, i);
				i = tlap.getPos()-1;
				ArrayList<Tree> temp_list = getHCParamList(tlap.getTree_list()); 
				Tree child = generateHCExpression(temp_list);
				child_tree.add(child);
			}else if(type.equals(Dictionary.PARAM) && next_type.equals(Dictionary.OP)){
				TreeListAndPos tlap = findExpressionTreeList(tree_list, i);
				i = tlap.getPos()-1;
				ArrayList<Tree> temp_list = getHCParamList(tlap.getTree_list()); 
				Tree child = generateHCExpression(temp_list);
				child_tree.add(child);
			}else if(type.equals(Dictionary.COMMA)){
				continue;
			}else{
				//throw new SyntaxErrorException();
				TreeListAndPos tlap = findExpressionTreeList(tree_list, i);
				i = tlap.getPos()-1;
				ArrayList<Tree> temp_list = getHCParamList(tlap.getTree_list()); 
				Tree child = generateHCExpression(temp_list);
				child_tree.add(child);
			}
			
		}
		now_tree.setChild_tree(child_tree);
		
		TreeAndPos tap = new TreeAndPos();
		tap.setTree(now_tree);
		
		
		if(semaphore == 0)
			tap.setPos(count_i);
		else
			tap.setPos(tree_list.size());
		
		return tap;
	}
	
	//初始化 将token串转成tree串
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
	
	//从tree中找出一个有效的表达式串
	private static TreeListAndPos findExpressionTreeList(ArrayList<Tree> list,int start_pos) throws SyntaxErrorException{
		ArrayList<Tree> expression_list = new ArrayList<Tree>();
		int semaphore=0,return_pos=0;
		for(int i=start_pos;i<list.size();i++){
			Tree now_tree = list.get(i);
			if(now_tree.getType().equals(Dictionary.LSB))
				semaphore++;
			if(now_tree.getType().equals(Dictionary.RSB))
				semaphore--;
			if(semaphore<0){
				return_pos = i;
				TreeListAndPos tlap = new TreeListAndPos();
				tlap.setPos(return_pos);
				tlap.setTree_list(expression_list);
				return tlap;
			}
			if(semaphore>0){
				expression_list.add(now_tree);
			}else if(semaphore==0){
				if(now_tree.getType().equals(Dictionary.COMMA)){
					return_pos = i;
					TreeListAndPos tlap = new TreeListAndPos();
					tlap.setPos(return_pos);
					tlap.setTree_list(expression_list);
					return tlap;
				}else{
					expression_list.add(now_tree);
				}
			}
		}
		TreeListAndPos tlap = new TreeListAndPos();
		tlap.setPos(list.size());
		tlap.setTree_list(expression_list);
		return tlap;
	}
}
