package com.spring.common.entity;

import java.util.HashMap;
import java.util.List;

public class MenuTree {
	
	private Integer id;
	private Integer parendId;
	private String state = "open";
	private String text;
	private List<MenuTree> children;
	private boolean checked;
	private HashMap<String, String> attributes;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParendId() {
		return parendId;
	}
	public void setParendId(Integer parendId) {
		this.parendId = parendId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<MenuTree> getChildren() {
		return children;
	}
	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	
}
