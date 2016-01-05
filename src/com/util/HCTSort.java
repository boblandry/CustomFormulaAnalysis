package com.util;

import java.util.ArrayList;
import java.util.Stack;
import com.entity.Dictionary;
import com.entity.Tree;

public class HCTSort {

	public static Stack<Tree> sort(ArrayList<Tree> HCParamList){
		
		Stack<Tree> s_operator = new Stack<Tree>();
		Stack<Tree> s_operand = new Stack<Tree>();
		
		for(int i=0;i<HCParamList.size();i++){
			Tree now_tree = HCParamList.get(i);
			String type = now_tree.getType();
			if(type.equals(Dictionary.OP) || type.equals(Dictionary.LRB) ||
					type.equals(Dictionary.RRB)){
				if(s_operator.isEmpty()){
					s_operator.push(now_tree);
				}else if(now_tree.getType().equals(Dictionary.LRB)){
					s_operator.push(now_tree);
				}else if(now_tree.getType().equals(Dictionary.RRB)){
					Tree temp = s_operator.peek();
					while(!temp.getType().equals(Dictionary.LRB)){
						s_operand.push(temp);
						s_operator.pop();
						temp =  s_operator.peek();
					}
					s_operator.pop();
				}else if(s_operator.peek().getType().equals(Dictionary.LRB)){
					s_operator.push(now_tree);
				}else if(compare(now_tree,s_operator.peek()) == 1){
					s_operator.push(now_tree);
				}else if(compare(now_tree,s_operator.peek()) != 1){
					while(!s_operator.isEmpty() && (compare(now_tree,s_operator.peek()) != 1 ||
							(s_operator.peek().getType().equals(Dictionary.LRB) ||
									s_operator.peek().getType().equals(Dictionary.RRB)))){
						Tree temp = s_operator.pop();
						s_operand.push(temp);
					}
					s_operator.push(now_tree);
				}
			}else{
				s_operand.push(now_tree);
			}
			
		}
		
		while(!s_operand.isEmpty())
			s_operator.push(s_operand.pop());
		
		return s_operator;
	}
	
	/**
	 * 比较t1和t2中操作符的优先级
	 * @param t1
	 * @param t2
	 * @return 
	 */
	private static int compare(Tree t1,Tree t2){
		if(getPriority(t1) < getPriority(t2)){
			return -1;
		}else if(getPriority(t1) > getPriority(t2)){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 弃用
	private static boolean in_low(Tree t){
		if(t.getName().equals("+") || t.getName().equals("-"))
			return true;
		return false;
	}
	*/
	private static int getPriority(Tree t){
		return Priority.getPriority(t.getName());
	}
}
