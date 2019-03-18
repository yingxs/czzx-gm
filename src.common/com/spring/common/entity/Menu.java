package com.spring.common.entity;

import java.util.HashMap;
import java.util.List;

public class Menu {

	private Long id;
	private Long parendId;
	private String text;
	private String url;
	private Integer sortCode;
	private List<Menu> children;
	private HashMap<String, String> attributes;
	public Menu(Long id, Long parendId, String text, String url) {
		super();
		this.id = id;
		this.parendId = parendId;
		this.text = text;
		this.url = url;
	}
	public Menu(Long id, Long parendId, String text, String url,
			List<Menu> children) {
		super();
		this.id = id;
		this.parendId = parendId;
		this.text = text;
		this.url = url;
		this.children = children;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParendId() {
		return parendId;
	}
	public void setParendId(Long parendId) {
		this.parendId = parendId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	
}
