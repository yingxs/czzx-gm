package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sysbuttons entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sysbuttons")
public class Sysbuttons implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String label;
	private String icon;
	private String code;
	private Integer sortCode;
	private String type;
	private String remark;
	private Integer status;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;
	private Timestamp modifytime;
	private Long modifyUserId;
	private String modifyUsername;

	// Constructors

	/** default constructor */
	public Sysbuttons() {
	}

	/** minimal constructor */
	public Sysbuttons(Integer sortCode, Integer status, Timestamp addtime,
			Long addUserId) {
		this.sortCode = sortCode;
		this.status = status;
		this.addtime = addtime;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Sysbuttons(String name, String label, String icon, String code,
			Integer sortCode, String type, String remark, Integer status,
			Timestamp addtime, Long addUserId, String addUsername,
			Timestamp modifytime, Long modifyUserId, String modifyUsername) {
		this.name = name;
		this.label = label;
		this.icon = icon;
		this.code = code;
		this.sortCode = sortCode;
		this.type = type;
		this.remark = remark;
		this.status = status;
		this.addtime = addtime;
		this.addUserId = addUserId;
		this.addUsername = addUsername;
		this.modifytime = modifytime;
		this.modifyUserId = modifyUserId;
		this.modifyUsername = modifyUsername;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "label", length = 50)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "icon", length = 50)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "code", length = 200)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "sortCode", nullable = false)
	public Integer getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "addUserId", nullable = false)
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

	@Column(name = "modifytime", length = 19)
	public Timestamp getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "modifyUserId")
	public Long getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "modifyUsername", length = 50)
	public String getModifyUsername() {
		return this.modifyUsername;
	}

	public void setModifyUsername(String modifyUsername) {
		this.modifyUsername = modifyUsername;
	}

}