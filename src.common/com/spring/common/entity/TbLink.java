package com.spring.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_link")
public class TbLink implements Serializable{
	
	private Long tlId;
	private String tlName;//链接名称
	private Integer tlCategory;//分类
	private String tlPicture;//封面图片
	private String tlUrl;//链接
	private Integer tlIndex;//排序
	private Timestamp tlAddTime;//添加时间
	private Integer tlAddUser;//添加人
	private Integer tlStatus;//状态 0删除 1正常
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tl_id",unique = true,nullable = false)
	public Long getTlId() {
		return tlId;
	}
	public void setTlId(Long tlId) {
		this.tlId = tlId;
	}
	@Column(name ="tl_name")
	public String getTlName() {
		return tlName;
	}
	public void setTlName(String tlName) {
		this.tlName = tlName;
	}
	@Column(name = "tl_category")
	public Integer getTlCategory() {
		return tlCategory;
	}
	public void setTlCategory(Integer tlCategory) {
		this.tlCategory = tlCategory;
	}
	@Column(name = "tl_picture")
	public String getTlPicture() {
		return tlPicture;
	}
	public void setTlPicture(String tlPicture) {
		this.tlPicture = tlPicture;
	}
	@Column(name = "tl_url")
	public String getTlUrl() {
		return tlUrl;
	}
	public void setTlUrl(String tlUrl) {
		this.tlUrl = tlUrl;
	}
	@Column(name = "tl_index")
	public Integer getTlIndex() {
		return tlIndex;
	}
	public void setTlIndex(Integer tlIndex) {
		this.tlIndex = tlIndex;
	}
	@Column(name = "tl_addtime")
	public Timestamp getTlAddTime() {
		return tlAddTime;
	}
	public void setTlAddTime(Timestamp tlAddTime) {
		this.tlAddTime = tlAddTime;
	}
	@Column(name = "tl_adduser")
	public Integer getTlAddUser() {
		return tlAddUser;
	}
	public void setTlAddUser(Integer tlAddUser) {
		this.tlAddUser = tlAddUser;
	}
	@Column(name = "tl_status")
	public Integer getTlStatus() {
		return tlStatus;
	}
	public void setTlStatus(Integer tlStatus) {
		this.tlStatus = tlStatus;
	}
	
	public TbLink(Long tlId, Integer tlCategory, String tlPicture, String tlUrl, Integer tlIndex, Timestamp tlAddTime,
			Integer tlAddUser) {
		super();
		this.tlId = tlId;
		this.tlCategory = tlCategory;
		this.tlPicture = tlPicture;
		this.tlUrl = tlUrl;
		this.tlIndex = tlIndex;
		this.tlAddTime = tlAddTime;
		this.tlAddUser = tlAddUser;
	}
	
	public TbLink(){
		super();
	}
	
}
