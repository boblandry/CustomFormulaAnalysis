package com.entity;

import java.util.Queue;
import java.util.Stack;
/**
 * �������ʹ�� com.util.Tokenizer4HybridOperation
 * ������������Ϻ󴫵ݵĶ���
 * param �����������
 * operator �������������
 * param_no ���������������������ĸ���
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
