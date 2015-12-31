package com.entity;

import java.util.ArrayList;

public class Tree {

	public static final String FUN = "Function";
	public static final String OP = "Operation";
	public static final String PARAM = "Param";
	
	private String type;
	private String name;
	private ArrayList<Tree> child_tree_list;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Tree> getChild_tree_list() {
		return child_tree_list;
	}
	public void setChild_tree(ArrayList<Tree> child_tree_list) {
		this.child_tree_list = child_tree_list;
	}
	
	
	
}
