package com.spring.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TbInformationCata entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_information_cata")
public class TbInformationCata implements java.io.Serializable {

	// Fields

	private Long ticId;//ID
	private String ticName;//分类名称
	private String ticMean;//分类描述
	private Long ticParentId;//父级id
	private Integer ticOrder;//显示排序
	private Long ticAddPreson;//创建人
	private Timestamp ticAddDate;//添加时间
	private Integer ticStatus;//状态,0：删除 1：正常  2：暂停

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tic_id", unique = true, nullable = false)
	public Long getTicId() {
		return this.ticId;
	}

	public void setTicId(Long ticId) {
		this.ticId = ticId;
	}

	@Column(name = "tic_name", nullable = false, length = 20)
	public String getTicName() {
		return this.ticName;
	}

	public void setTicName(String ticName) {
		this.ticName = ticName;
	}

	@Column(name = "tic_parent_id", nullable = false)
	public Long getTicParentId() {
		return this.ticParentId;
	}

	public void setTicParentId(Long ticParentId) {
		this.ticParentId = ticParentId;
	}

	@Column(name = "tic_order")
	public Integer getTicOrder() {
		return this.ticOrder;
	}

	public void setTicOrder(Integer ticOrder) {
		this.ticOrder = ticOrder;
	}

	@Column(name = "tic_add_date", nullable = false, length = 19)
	public Timestamp getTicAddDate() {
		return this.ticAddDate;
	}

	public void setTicAddDate(Timestamp ticAddDate) {
		this.ticAddDate = ticAddDate;
	}

	@Column(name = "tic_status", nullable = false)
	public Integer getTicStatus() {
		return this.ticStatus;
	}

	public void setTicStatus(Integer ticStatus) {
		this.ticStatus = ticStatus;
	}
	
	@Column(name = "tic_add_preson")
	public Long getTicAddPreson() {
		return ticAddPreson;
	}

	public void setTicAddPreson(Long ticAddPreson) {
		this.ticAddPreson = ticAddPreson;
	}
	@Column(name = "tic_mean")
	public String getTicMean() {
		return ticMean;
	}

	public void setTicMean(String ticMean) {
		this.ticMean = ticMean;
	}

	public TbInformationCata(Long ticId, String ticName, String ticMean,
			Long ticParentId, Integer ticOrder, Long ticAddPreson,
			Timestamp ticAddDate, Integer ticStatus) {
		super();
		this.ticId = ticId;
		this.ticName = ticName;
		this.ticMean = ticMean;
		this.ticParentId = ticParentId;
		this.ticOrder = ticOrder;
		this.ticAddPreson = ticAddPreson;
		this.ticAddDate = ticAddDate;
		this.ticStatus = ticStatus;
	}

	public TbInformationCata() {
		super();
	}

}