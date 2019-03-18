package com.spring.common.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Sysmenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sysmenu")
public class Sysmenu implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer parentId;
	private String name;
	private String label;
	private String icon;
	private Integer type;
	private String url;
	private Integer target;
	private Integer allowEdit;
	private Integer allowDelete;
	private Integer sortCode;
	private Integer status;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;
	private Timestamp modifytime;
	private Long modifyUserId;
	private String modifyUsername;
	private List<Sysmenu> children;
	private boolean isChecked = false;
	// Constructors

	/** default constructor */
	public Sysmenu() {
	}

	/** minimal constructor */
	public Sysmenu(Integer parentId, String name, String url, Timestamp addtime) {
		this.parentId = parentId;
		this.name = name;
		this.url = url;
		this.addtime = addtime;
	}

	/** full constructor */
	public Sysmenu(Integer parentId, String name, String label, String icon,
			Integer type, String url, Integer target, Integer allowEdit,
			Integer allowDelete, Integer sortCode, Integer status,
			Timestamp addtime, Long addUserId, String addUsername,
			Timestamp modifytime, Long modifyUserId, String modifyUsername) {
		this.parentId = parentId;
		this.name = name;
		this.label = label;
		this.icon = icon;
		this.type = type;
		this.url = url;
		this.target = target;
		this.allowEdit = allowEdit;
		this.allowDelete = allowDelete;
		this.sortCode = sortCode;
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

	@Column(name = "parentId", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "label", length = 20)
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

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "url", nullable = false, length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "target")
	public Integer getTarget() {
		return this.target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	@Column(name = "allowEdit")
	public Integer getAllowEdit() {
		return this.allowEdit;
	}

	public void setAllowEdit(Integer allowEdit) {
		this.allowEdit = allowEdit;
	}

	@Column(name = "allowDelete")
	public Integer getAllowDelete() {
		return this.allowDelete;
	}

	public void setAllowDelete(Integer allowDelete) {
		this.allowDelete = allowDelete;
	}

	@Column(name = "sortCode")
	public Integer getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "addtime", nullable = false, length = 19)
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
	
	@Column(name = "addUsername", length = 20)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	@Column(name = "modifyUsername", length = 20)
	public String getModifyUsername() {
		return this.modifyUsername;
	}

	public void setModifyUsername(String modifyUsername) {
		this.modifyUsername = modifyUsername;
	}
	@Transient
	public List<Sysmenu> getChildren() {
		return children;
	}

	public void setChildren(List<Sysmenu> children) {
		this.children = children;
	}
	@Transient
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}