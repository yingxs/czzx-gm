package com.spring.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbRegion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_region")
public class TbRegion implements java.io.Serializable {

	// Fields

	private Integer regionId;
	private Integer parentId;
	private String regionName;
	private Boolean regionType;
	private Short agencyId;

	// Constructors

	/** default constructor */
	public TbRegion() {
	}

	/** full constructor */
	public TbRegion(Integer parentId, String regionName, Boolean regionType,
			Short agencyId) {
		this.parentId = parentId;
		this.regionName = regionName;
		this.regionType = regionType;
		this.agencyId = agencyId;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "region_id", unique = true, nullable = false)
	public Integer getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	@Column(name = "parent_id", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "region_name", nullable = false, length = 120)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "region_type", nullable = false)
	public Boolean getRegionType() {
		return this.regionType;
	}

	public void setRegionType(Boolean regionType) {
		this.regionType = regionType;
	}

	@Column(name = "agency_id", nullable = false)
	public Short getAgencyId() {
		return this.agencyId;
	}

	public void setAgencyId(Short agencyId) {
		this.agencyId = agencyId;
	}

}