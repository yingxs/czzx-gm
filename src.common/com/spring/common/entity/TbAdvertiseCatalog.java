package com.spring.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
*    
* 项目名称：juzi-gm   
* 类名称：TbAdvertiseCatalog   
* 类描述：   广告板块表
* 创建人：lm 
* 创建时间：2015年8月26日 下午8:07:10   
* @version    
*
 */
@Entity
@Table(name="tb_advertise_catalog")
public class TbAdvertiseCatalog implements java.io.Serializable{
	private Long tacId;
	private String tacName;
	private String tacDesp;
	private Timestamp tacAddDate;
	private Long tacAddPerson;
	private Integer tacStatus;//状态,1：正常 0: 删除
	
	private String tacAddPersonName;
	
	/** default constructor */
	public TbAdvertiseCatalog() {
	}

	/** full constructor */
	public TbAdvertiseCatalog(Long tacId, String tacName, String tacDesp,
			Timestamp tacAddDate, Long tacAddPerson, Integer tacStatus,
			String tacAddPersonName) {
		this.tacId = tacId;
		this.tacName = tacName;
		this.tacDesp = tacDesp;
		this.tacAddDate = tacAddDate;
		this.tacAddPerson = tacAddPerson;
		this.tacStatus = tacStatus;
		this.tacAddPersonName = tacAddPersonName;
	}

	@Id
	@GeneratedValue
	@Column(name = "tac_id", unique = true, nullable = false, length = 8)
	public Long getTacId() {
		return tacId;
	}

	public void setTacId(Long tacId) {
		this.tacId = tacId;
	}

	@Column(name = "tac_name", nullable = false)
	public String getTacName() {
		return tacName;
	}

	public void setTacName(String tacName) {
		this.tacName = tacName;
	}

	@Column(name = "tac_desp")
	public String getTacDesp() {
		return tacDesp;
	}

	public void setTacDesp(String tacDesp) {
		this.tacDesp = tacDesp;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "tac_add_date", nullable = false, length = 19)
	public Timestamp getTacAddDate() {
		return tacAddDate;
	}

	public void setTacAddDate(Timestamp tacAddDate) {
		this.tacAddDate = tacAddDate;
	}

	@Column(name = "tac_add_person")
	public Long getTacAddPerson() {
		return tacAddPerson;
	}

	public void setTacAddPerson(Long tacAddPerson) {
		this.tacAddPerson = tacAddPerson;
	}

	@Column(name = "tac_status", nullable = false)
	public Integer getTacStatus() {
		return tacStatus;
	}

	public void setTacStatus(Integer tacStatus) {
		this.tacStatus = tacStatus;
	}

	@Transient
	public String getTacAddPersonName() {
		return tacAddPersonName;
	}

	public void setTacAddPersonName(String tacAddPersonName) {
		this.tacAddPersonName = tacAddPersonName;
	}

}
