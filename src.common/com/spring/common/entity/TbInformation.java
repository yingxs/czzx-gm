package com.spring.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbInformation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_information", catalog = "xiaomianao")
public class TbInformation implements java.io.Serializable {

	// Fields

	private Long tiId;//id
	private Long tiCataId;//分类id
	private String tiTitle;//资讯标题
	private String tiContent;//资讯内容
	private Long tiAddPreson;//创建人
	private Timestamp tiAddDate;//创建时间
	private Integer tiStatus;//状态 , 0：删除 1：正常

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ti_id", unique = true, nullable = false)
	public Long getTiId() {
		return this.tiId;
	}

	public void setTiId(Long tiId) {
		this.tiId = tiId;
	}

	@Column(name = "ti_cata_id", nullable = false)
	public Long getTiCataId() {
		return this.tiCataId;
	}

	public void setTiCataId(Long tiCataId) {
		this.tiCataId = tiCataId;
	}

	@Column(name = "ti_title", nullable = false, length = 50)
	public String getTiTitle() {
		return this.tiTitle;
	}

	public void setTiTitle(String tiTitle) {
		this.tiTitle = tiTitle;
	}

	@Column(name = "ti_content", nullable = false, length = 65535)
	public String getTiContent() {
		return this.tiContent;
	}

	public void setTiContent(String tiContent) {
		this.tiContent = tiContent;
	}

	@Column(name = "ti_add_date", nullable = false, length = 19)
	public Timestamp getTiAddDate() {
		return this.tiAddDate;
	}

	public void setTiAddDate(Timestamp tiAddDate) {
		this.tiAddDate = tiAddDate;
	}

	@Column(name = "ti_status", nullable = false)
	public Integer getTiStatus() {
		return this.tiStatus;
	}

	public void setTiStatus(Integer tiStatus) {
		this.tiStatus = tiStatus;
	}
	@Column(name = "ti_add_preson")
	public Long getTiAddPreson() {
		return tiAddPreson;
	}

	public void setTiAddPreson(Long tiAddPreson) {
		this.tiAddPreson = tiAddPreson;
	}

	public TbInformation(Long tiId, Long tiCataId, String tiTitle,
			String tiContent, Long tiAddPreson, Timestamp tiAddDate,
			Integer tiStatus) {
		super();
		this.tiId = tiId;
		this.tiCataId = tiCataId;
		this.tiTitle = tiTitle;
		this.tiContent = tiContent;
		this.tiAddPreson = tiAddPreson;
		this.tiAddDate = tiAddDate;
		this.tiStatus = tiStatus;
	}

	public TbInformation() {
		super();
	}

}