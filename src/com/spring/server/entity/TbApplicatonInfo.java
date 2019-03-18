package com.spring.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *   应用信息表 entity.
 */
@Entity
@Table(name = "tb_applicaton_info")
public class TbApplicatonInfo {
	private Long taiId;//应用信息表主键Id
	private String taiName; //应用名称 1:系统
	private Integer taiType;//类型
	private String taiWebAddr;//应用地址
	private String taiIcon;//应用图标 80*80
	private Timestamp taiAddDate;//创建时间
	private Integer taiAddCount;//加载数量
	private Integer taiStatus; //状态 1：正常 0: 删除
	private String taiContent;//应用描述
	private Long taiDownloads;//下载量
	private Long taiPageCount;//浏览量
	private String taiDesp;//应用介绍
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tai_id")
	public Long getTaiId() {
		return taiId;
	}
	public void setTaiId(Long taiId) {
		this.taiId = taiId;
	}
	@Column(name = "tai_name")
	public String getTaiName() {
		return taiName;
	}
	public void setTaiName(String taiName) {
		this.taiName = taiName;
	}
	@Column(name = "tai_type")
	public Integer getTaiType() {
		return taiType;
	}
	public void setTaiType(Integer taiType) {
		this.taiType = taiType;
	}
	@Column(name = "tai_web_addr")
	public String getTaiWebAddr() {
		return taiWebAddr;
	}
	public void setTaiWebAddr(String taiWebAddr) {
		this.taiWebAddr = taiWebAddr;
	}
	@Column(name = "tai_icon")
	public String getTaiIcon() {
		return taiIcon;
	}
	public void setTaiIcon(String taiIcon) {
		this.taiIcon = taiIcon;
	}
	@Column(name = "tai_add_date")
	public Timestamp getTaiAddDate() {
		return taiAddDate;
	}
	public void setTaiAddDate(Timestamp taiAddDate) {
		this.taiAddDate = taiAddDate;
	}
	@Column(name = "tai_add_count")
	public Integer getTaiAddCount() {
		return taiAddCount;
	}
	public void setTaiAddCount(Integer taiAddCount) {
		this.taiAddCount = taiAddCount;
	}
	@Column(name = "tai_status")
	public Integer getTaiStatus() {
		return taiStatus;
	}
	public void setTaiStatus(Integer taiStatus) {
		this.taiStatus = taiStatus;
	}
	@Column(name = "tai_content")
	public String getTaiContent() {
		return taiContent;
	}
	public void setTaiContent(String taiContent) {
		this.taiContent = taiContent;
	}
	@Column(name = "tai_downloads")
	public Long getTaiDownloads() {
		return taiDownloads;
	}
	public void setTaiDownloads(Long taiDownloads) {
		this.taiDownloads = taiDownloads;
	}
	@Column(name = "tai_page_count")
	public Long getTaiPageCount() {
		return taiPageCount;
	}
	public void setTaiPageCount(Long taiPageCount) {
		this.taiPageCount = taiPageCount;
	}
	@Column(name = "tai_desp")
	public String getTaiDesp() {
		return taiDesp;
	}
	public void setTaiDesp(String taiDesp) {
		this.taiDesp = taiDesp;
	}
	public TbApplicatonInfo(Long taiId, String taiName, Integer taiType, String taiWebAddr, String taiIcon,
			Timestamp taiAddDate, Integer taiAddCount, Integer taiStatus, String taiContent, Long taiDownloads,
			Long taiPageCount, String taiDesp) {
		super();
		this.taiId = taiId;
		this.taiName = taiName;
		this.taiType = taiType;
		this.taiWebAddr = taiWebAddr;
		this.taiIcon = taiIcon;
		this.taiAddDate = taiAddDate;
		this.taiAddCount = taiAddCount;
		this.taiStatus = taiStatus;
		this.taiContent = taiContent;
		this.taiDownloads = taiDownloads;
		this.taiPageCount = taiPageCount;
		this.taiDesp = taiDesp;
	}
	public TbApplicatonInfo() {
		super();
	}
	
	
}
