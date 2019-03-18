package com.spring.common.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Appendpropertyinstance entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "appendpropertyinstance")
public class Appendpropertyinstance implements java.io.Serializable {

	// Fields

	private Integer id;
	private Long controlId;
	private String value;
	private Integer key;

	// Constructors

	/** default constructor */
	public Appendpropertyinstance() {
	}

	/** full constructor */
	public Appendpropertyinstance(Long controlId, String value, Integer key) {
		this.controlId = controlId;
		this.value = value;
		this.key = key;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "controlId")
	public Long getControlId() {
		return this.controlId;
	}

	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}

	@Column(name = "value", length = 250)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "key")
	public Integer getKey() {
		return this.key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

}