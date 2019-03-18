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
 * 楼层控制器
 *
 * @author wanghao
 * @Date 2019-03-08 11:44:38
 */
@Entity
@Table(name = "tb_floor_info")
public class TbFloorInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tfiId;
	//楼层
	private String tfiName;
	//状态
	private Integer tfiStatus;
	//所属楼宇
	private Long tfiBuildingId;
	private Long tfiNameId;
	
	// Constructors

	/** default constructor */
	public TbFloorInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tfi_id", unique = true, nullable = false, length=19)
	public Long getTfiId() {
		return this.tfiId;
	}
	
	public void setTfiId(Long tfiId) {
		this.tfiId = tfiId;
	}
	
	@Column(name = "tfi_name",  nullable = false, length=20)
	public String getTfiName() {
		return this.tfiName;
	}
	
	public void setTfiName(String tfiName) {
		this.tfiName = tfiName;
	}
	
	@Column(name = "tfi_status",  nullable = false, length=10)
	public Integer getTfiStatus() {
		return this.tfiStatus;
	}
	
	public void setTfiStatus(Integer tfiStatus) {
		this.tfiStatus = tfiStatus;
	}
	
	@Column(name = "tfi_building_id",  nullable = false, length=19)
	public Long getTfiBuildingId() {
		return this.tfiBuildingId;
	}
	
	public void setTfiBuildingId(Long tfiBuildingId) {
		this.tfiBuildingId = tfiBuildingId;
	}
	
	@Column(name = "tfi_name_id",  nullable = false, length=19)
	public Long getTfiNameId() {
		return this.tfiNameId;
	}
	
	public void setTfiNameId(Long tfiNameId) {
		this.tfiNameId = tfiNameId;
	}
	
	
}