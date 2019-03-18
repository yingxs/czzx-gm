package com.spring.common.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_notice_infomation")
public class Notice implements Serializable {

	private static final long serialVersionUID = -179199252426986007L;

	private Long tniId;

	private String tniTitle;

	private String tniType;

	private String tniRecvType;

	private String tniRecvPersons;

	private String tniLogo;

	private String tniContent;

	private Timestamp tniAddDate;

	private String tniAttachment;

	private Timestamp tniStartDate;

	private Timestamp tniEndDate;

	private Integer tniStatus;

	private String tni_add_date_str;
	
	private Integer tniTop;
	
	private Integer tniHead;
	
	private Integer tniAddUser;
	
	private String tniCode;
	

	@Id
	@GeneratedValue
	@Column(name = "tni_id", unique = true, nullable = false)
	public Long getTniId() {
		return tniId;
	}

	@Transient
	public String getTni_add_date_str() {
		return tni_add_date_str;
	}

	public void setTni_add_date_str(String tni_add_date_str) {
		this.tni_add_date_str = tni_add_date_str;
	}

	public void setTniId(Long tniId) {
		this.tniId = tniId;
	}

	@Column(name = "tni_title")
	public String getTniTitle() {
		return tniTitle;
	}

	public void setTniTitle(String tniTitle) {
		this.tniTitle = tniTitle;
	}

	@Column(name = "tni_type")
	public String getTniType() {
		return tniType;
	}

	public void setTniType(String tniType) {
		this.tniType = tniType;
	}

	@Column(name = "tni_recv_type")
	public String getTniRecvType() {
		return tniRecvType;
	}

	public void setTniRecvType(String tniRecvType) {
		this.tniRecvType = tniRecvType;
	}

	@Column(name = "tni_recv_persons")
	public String getTniRecvPersons() {
		return tniRecvPersons;
	}

	public void setTniRecvPersons(String tniRecvPersons) {
		this.tniRecvPersons = tniRecvPersons;
	}

	@Column(name = "tni_logo")
	public String getTniLogo() {
		return tniLogo;
	}

	public void setTniLogo(String tniLogo) {
		this.tniLogo = tniLogo;
	}

	@Column(name = "tni_content")
	public String getTniContent() {
		return tniContent;
	}

	public void setTniContent(String tniContent) {
		this.tniContent = tniContent;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "tni_add_date")
	public Timestamp getTniAddDate() {
		return tniAddDate;
	}

	public void setTniAddDate(Timestamp tniAddDate) {
		if (tniAddDate != null) {
			this.tni_add_date_str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tniAddDate);
		}
		this.tniAddDate = tniAddDate;
	}

	@Column(name = "tni_attachment")
	public String getTniAttachment() {
		return tniAttachment;
	}

	public void setTniAttachment(String tniAttachment) {
		this.tniAttachment = tniAttachment;
	}

	@Column(name = "tni_start_date")
	public Timestamp getTniStartDate() {
		return tniStartDate;
	}

	public void setTniStartDate(Timestamp tniStartDate) {
		this.tniStartDate = tniStartDate;
	}

	@Column(name = "tni_end_date")
	public Timestamp getTniEndDate() {
		return tniEndDate;
	}

	public void setTniEndDate(Timestamp tniEndDate) {
		this.tniEndDate = tniEndDate;
	}

	@Column(name = "tni_status")
	public Integer getTniStatus() {
		return tniStatus;
	}

	public void setTniStatus(Integer tniStatus) {
		this.tniStatus = tniStatus;
	}
	
	@Column(name = "tni_top")
	public Integer getTniTop() {
		return tniTop;
	}

	public void setTniTop(Integer tniTop) {
		this.tniTop = tniTop;
	}

	@Column(name = "tni_head")
	public Integer getTniHead() {
		return tniHead;
	}

	public void setTniHead(Integer tniHead) {
		this.tniHead = tniHead;
	}
	
	
	@Column(name = "tni_code")
	public String getTniCode() {
		return tniCode;
	}

	public void setTniCode(String tniCode) {
		this.tniCode = tniCode;
	}

	@Column(name = "tni_add_user")
	public Integer getTniAddUser() {
		return tniAddUser;
	}

	public void setTniAddUser(Integer tniAddUser) {
		this.tniAddUser = tniAddUser;
	}


	public Notice(Long tniId, String tniTitle, String tniType, String tniRecvType, String tniRecvPersons,
			String tniLogo, String tniContent, Timestamp tniAddDate, String tniAttachment, Timestamp tniStartDate,
			Timestamp tniEndDate, Integer tniStatus, String tni_add_date_str, Integer tniTop, Integer tniHead,
			Integer tniAddUser, String tniCode) {
		super();
		this.tniId = tniId;
		this.tniTitle = tniTitle;
		this.tniType = tniType;
		this.tniRecvType = tniRecvType;
		this.tniRecvPersons = tniRecvPersons;
		this.tniLogo = tniLogo;
		this.tniContent = tniContent;
		this.tniAddDate = tniAddDate;
		this.tniAttachment = tniAttachment;
		this.tniStartDate = tniStartDate;
		this.tniEndDate = tniEndDate;
		this.tniStatus = tniStatus;
		this.tni_add_date_str = tni_add_date_str;
		this.tniTop = tniTop;
		this.tniHead = tniHead;
		this.tniAddUser = tniAddUser;
		this.tniCode = tniCode;
	}

	public Notice() {
		super();
	}

	@Override
	public String toString() {
		return "Notice [tniId=" + tniId + ", tniTitle=" + tniTitle + ", tniType=" + tniType + ", tniRecvType=" + tniRecvType + ", tniRecvPersons=" + tniRecvPersons + ", tniLogo=" + tniLogo + ", tniContent=" + tniContent + ", tniAddDate=" + tniAddDate + ", tniAttachment=" + tniAttachment + ", tniStartDate=" + tniStartDate + ", tniEndDate=" + tniEndDate + ", tniStatus=" + tniStatus + "]";
	}

}
