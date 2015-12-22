package com.entity;

import java.util.Queue;
import java.util.Stack;
/**
 * 混合运算使用 com.util.Tokenizer4HybridOperation
 * 解析器解析完毕后传递的对象
 * param 用来保存参数
 * operator 用来保存操作符
 * param_no 用来保存操作符所需参数的个数
 * 已废弃！
 * @author LichKing
 */
public class TokenizerInfo4HybridOperation {

	
	private Stack param;
	private Queue operator;
	private Queue param_no;
	
	public Stack getParam() {
		return param;
	}
	public void setParam(Stack param) {
		this.param = param;
	}
	public Queue getOperator() {
		return operator;
	}
	public void setOperator(Queue operator) {
		this.operator = operator;
	}
	public Queue getParam_no() {
		return param_no;
	}
	public void setParam_no(Queue param_no) {
		this.param_no = param_no;
	}
	
	
	
}
