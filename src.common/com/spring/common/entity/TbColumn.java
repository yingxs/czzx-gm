package com.spring.common.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbColumn entity.
 */
@Entity
@Table(name = "tb_column")
public class TbColumn {
	private Integer tcId;
	private Integer tcParentId;
	private String tcName;
	private String tcType;
	private String tcIndex;
	private Date tcAddtime;
	private Integer tcAdduser;
	private Integer tcStatus;
	private List<TbColumn> children;
	private String tcContent;
	
	private String parentName;
	
	/**
	 * @return the parentName
	 */
	@Transient
	public String getParentName() {
		return parentName;
	}

	@Transient
	public List<TbColumn> getChildren() {
		return children;
	}

	public void setChildren(List<TbColumn> children) {
		this.children = children;
	}
	
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public TbColumn() {
	}
	
	public TbColumn(Integer tcId, Integer tcParentId, String tcName, String tcType, String tcIndex, Date tcAddtime, Integer tcAdduser, Integer tcStatus, String taTopStr, String taHeadlineStr, String addtimeString, String addUserName) {
		super();
		this.tcId = tcId;
		this.tcParentId = tcParentId;
		this.tcName = tcName;
		this.tcType = tcType;
		this.tcIndex = tcIndex;
		this.tcAddtime = tcAddtime;
		this.tcAdduser = tcAdduser;
		this.tcStatus = tcStatus;
	}
	/**
	 * @return the tcId
	 */
	@Id
	@GeneratedValue
	@Column(name = "tc_id", unique = true, nullable = false)
	public Integer getTcId() {
		return tcId;
	}
	/**
	 * @param tcId the tcId to set
	 */
	@Id
	@GeneratedValue
	@Column(name = "tc_id", unique = true, nullable = false)
	public void setTcId(Integer tcId) {
		this.tcId = tcId;
	}
	/**
	 * @return the tcParentId
	 */
	@Column(name = "tc_parent_id", length = 8)
	public Integer getTcParentId() {
		return tcParentId;
	}
	/**
	 * @param tcParentId the tcParentId to set
	 */
	public void setTcParentId(Integer tcParentId) {
		this.tcParentId = tcParentId;
	}
	/**
	 * @return the tcName
	 */
	@Column(name = "tc_name", length = 50)
	public String getTcName() {
		return tcName;
	}
	/**
	 * @param tcName the tcName to set
	 */
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	/**
	 * @return the tcType
	 */
	@Column(name = "tc_type", length = 4)
	public String getTcType() {
		return tcType;
	}
	/**
	 * @param tcType the tcType to set
	 */
	public void setTcType(String tcType) {
		this.tcType = tcType;
	}
	/**
	 * @return the tcIndex
	 */
	@Column(name = "tc_index", length = 4)
	public String getTcIndex() {
		return tcIndex;
	}
	/**
	 * @param tcIndex the tcIndex to set
	 */
	public void setTcIndex(String tcIndex) {
		this.tcIndex = tcIndex;
	}
	/**
	 * @return the tcAddtime
	 */
	@Column(name = "tc_addtime")
	public Date getTcAddtime() {
		return tcAddtime;
	}
	/**
	 * @param tcAddtime the tcAddtime to set
	 */
	public void setTcAddtime(Date tcAddtime) {
		this.tcAddtime = tcAddtime;
	}
	/**
	 * @return the tcAdduser
	 */
	@Column(name = "tc_adduser", length = 8)
	public Integer getTcAdduser() {
		return tcAdduser;
	}
	/**
	 * @param tcAdduser the tcAdduser to set
	 */
	public void setTcAdduser(Integer tcAdduser) {
		this.tcAdduser = tcAdduser;
	}
	/**
	 * @return the tcStatus
	 */
	@Column(name = "tc_status", length = 4)
	public Integer getTcStatus() {
		return tcStatus;
	}
	/**
	 * @param tcStatus the tcStatus to set
	 */
	public void setTcStatus(Integer tcStatus) {
		this.tcStatus = tcStatus;
	}
	/**
	 * @return the addtimeString
	 */
	@Column(name = "tc_content", length = 256)
	public String getTcContent() {
		return tcContent;
	}

	public void setTcContent(String tcContent) {
		this.tcContent = tcContent;
	}

	
}
