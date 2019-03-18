package com.spring.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_star")
public class TbStar implements Serializable{

	private Integer tsId;
	private String tsUserName;
	private Integer tsType;
	private Date tsAddTime;
	private Integer tsStatus;
	private Long tsAdduser;
	private String tsDesc;
	private String tsPhoto;
	private String tsContent;

	public TbStar() {
		super();
	}

	/**
	 * @return the tsId
	 */
	@Id
	@GeneratedValue
	@Column(name = "ts_id", unique = true, nullable = false)
	public Integer getTsId() {
		return tsId;
	}

	/**
	 * @param tsId
	 *            the tsId to set
	 */
	public void setTsId(Integer tsId) {
		this.tsId = tsId;
	}


	@Column(name = "ts_user_name", nullable = false, length = 200)
	public String getTsUserName() {
		return tsUserName;
	}

	public void setTsUserName(String tsUserName) {
		this.tsUserName = tsUserName;
	}

	/**
	 * @return the tsType
	 */
	@Column(name = "ts_type", nullable = false, length = 4)
	public Integer getTsType() {
		return tsType;
	}

	/**
	 * @param tsType
	 *            the tsType to set
	 */
	public void setTsType(Integer tsType) {
		this.tsType = tsType;
	}

	/**
	 * @return the tsAddTime
	 */
	@Column(name = "ts_addtime")
	public Date getTsAddTime() {
		return tsAddTime;
	}

	/**
	 * @param tsAddTime
	 *            the tsAddTime to set
	 */
	public void setTsAddTime(Date tsAddTime) {
		this.tsAddTime = tsAddTime;
	}

	/**
	 * @return the tsStatus
	 */
	@Column(name = "ts_status", length = 4)
	public Integer getTsStatus() {
		return tsStatus;
	}

	/**
	 * @param tsStatus
	 *            the tsStatus to set
	 */
	public void setTsStatus(Integer tsStatus) {
		this.tsStatus = tsStatus;
	}

	/**
	 * @return the tsAdduser
	 */
	@Column(name = "ts_adduser", length = 200)
	public Long getTsAdduser() {
		return tsAdduser;
	}

	/**
	 * @param tsAdduser
	 *            the tsAdduser to set
	 */
	public void setTsAdduser(Long tsAdduser) {
		this.tsAdduser = tsAdduser;
	}

	/**
	 * @return the tsDesc
	 */
	@Column(name = "ts_desc", length = 256)
	public String getTsDesc() {
		return tsDesc;
	}

	/**
	 * @param tsDesc
	 *            the tsDesc to set
	 */
	public void setTsDesc(String tsDesc) {
		this.tsDesc = tsDesc;
	}

	/**
	 * @return the tsPhoto
	 */
	@Column(name = "ts_photo", length = 256)
	public String getTsPhoto() {
		return tsPhoto;
	}

	/**
	 * @param tsPhoto
	 *            the tsPhoto to set
	 */

	public void setTsPhoto(String tsPhoto) {
		this.tsPhoto = tsPhoto;
	}

	/**
	 * @return the tsContent
	 */
	@Column(name = "ts_content", length = 256)
	public String getTsContent() {
		return tsContent;
	}

	/**
	 * @param tsContent
	 *            the tsContent to set
	 */
	public void setTsContent(String tsContent) {
		this.tsContent = tsContent;
	}

}
