package com.spring.common.basequery;

import java.io.Serializable;
import java.util.List;

public class TreeObj implements Serializable {

	private static final long serialVersionUID = -2786841490225669520L;

	private String id;

	private String text;

	private List<TreeObj> children;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the children
	 */
	public List<TreeObj> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<TreeObj> children) {
		this.children = children;
	}

}
