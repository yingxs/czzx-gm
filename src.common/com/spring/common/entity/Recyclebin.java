package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Recyclebin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "recyclebin")
public class Recyclebin implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String database;
	private String table;
	private Long fieldId;
	private String fieldValue;
	private String remark;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;

	// Constructors

	/** default constructor */
	public Recyclebin() {
	}

	/** full constructor */
	public Recyclebin(String name, String database, String table, Long fieldId,
			String fieldValue, String remark, Timestamp addtime,
			Long addUserId, String addUsername) {
		this.name = name;
		this.database = database;
		this.table = table;
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
		this.remark = remark;
		this.addtime = addtime;
		this.addUserId = addUserId;
		this.addUsername = addUsername;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "database", length = 50)
	public String getDatabase() {
		return this.database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Column(name = "table", length = 50)
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	@Column(name = "fieldId")
	public Long getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	@Column(name = "fieldValue", length = 50)
	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "addUserId")
	public Long getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "addUsername", length = 50)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}

}