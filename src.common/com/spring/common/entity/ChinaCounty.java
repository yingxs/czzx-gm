package com.spring.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ChinaCounty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "china_county")
public class ChinaCounty implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String oname;
	private Integer cid;

	// Constructors

	/** default constructor */
	public ChinaCounty() {
	}

	/** full constructor */
	public ChinaCounty(String oname, Integer cid) {
		this.oname = oname;
		this.cid = cid;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "oid", unique = true, nullable = false)
	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	@Column(name = "oname", length = 100)
	public String getOname() {
		return this.oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	@Column(name = "cid")
	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}