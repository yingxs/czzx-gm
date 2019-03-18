package com.spring.server.entity;

import static javax.persistence.GenerationType.IDENTITY;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 * 教学楼控制器
 *
 * @author wanghao
 * @Date 2019-03-08 09:49:40
 */
@Entity
@Table(name = "tb_building_info")
public class TbBuildingInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tbiId;
	//名称
	private String tbiName;
	//状态
	private Integer tbiStatus;
	//所属校区
	private Long tbiRegionId;
	
	// Constructors

	/** default constructor */
	public TbBuildingInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tbi_id", unique = true, nullable = false, length=19)
	public Long getTbiId() {
		return this.tbiId;
	}
	
	public void setTbiId(Long tbiId) {
		this.tbiId = tbiId;
	}
	
	@Column(name = "tbi_name",  nullable = false, length=20)
	public String getTbiName() {
		return this.tbiName;
	}
	
	public void setTbiName(String tbiName) {
		this.tbiName = tbiName;
	}
	
	@Column(name = "tbi_status",  nullable = false, length=10)
	public Integer getTbiStatus() {
		return this.tbiStatus;
	}
	
	public void setTbiStatus(Integer tbiStatus) {
		this.tbiStatus = tbiStatus;
	}
	
	@Column(name = "tbi_region_id",  nullable = false, length=19)
	public Long getTbiRegionId() {
		return this.tbiRegionId;
	}
	
	public void setTbiRegionId(Long tbiRegionId) {
		this.tbiRegionId = tbiRegionId;
	}
	
	
}