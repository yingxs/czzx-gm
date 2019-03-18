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
 * 教室
 *
 * @author wanghao
 * @Date 2019-03-08 11:45:36
 */
@Entity
@Table(name = "tb_classroom_info")
public class TbClassroomInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tciId;
	//教学楼id
	private Long tciBuildingId;
	//楼层
	private Long tciFloorId;
	//教师名称
	private String tciClassname;
	//容纳座位数
	private Integer tciCount;
	//状态
	private Integer tciStatus;
	//使用状态
	private String tciUseStatus;
	//使用班级
	private Long tciUserClaassId;
	
	// Constructors

	/** default constructor */
	public TbClassroomInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tci_id", unique = true, nullable = false, length=19)
	public Long getTciId() {
		return this.tciId;
	}
	
	public void setTciId(Long tciId) {
		this.tciId = tciId;
	}
	
	@Column(name = "tci_building_id",  nullable = false, length=19)
	public Long getTciBuildingId() {
		return this.tciBuildingId;
	}
	
	public void setTciBuildingId(Long tciBuildingId) {
		this.tciBuildingId = tciBuildingId;
	}
	
	@Column(name = "tci_floor_id",  nullable = false, length=19)
	public Long getTciFloorId() {
		return this.tciFloorId;
	}
	
	public void setTciFloorId(Long tciFloorId) {
		this.tciFloorId = tciFloorId;
	}
	
	@Column(name = "tci_classname",  nullable = false, length=20)
	public String getTciClassname() {
		return this.tciClassname;
	}
	
	public void setTciClassname(String tciClassname) {
		this.tciClassname = tciClassname;
	}
	
	@Column(name = "tci_count",  nullable = false, length=10)
	public Integer getTciCount() {
		return this.tciCount;
	}
	
	public void setTciCount(Integer tciCount) {
		this.tciCount = tciCount;
	}
	
	@Column(name = "tci_status",  nullable = false, length=10)
	public Integer getTciStatus() {
		return this.tciStatus;
	}
	
	public void setTciStatus(Integer tciStatus) {
		this.tciStatus = tciStatus;
	}
	
	@Column(name = "tci_use_status",  nullable = false, length=0)
	public String getTciUseStatus() {
		return this.tciUseStatus;
	}
	
	public void setTciUseStatus(String tciUseStatus) {
		this.tciUseStatus = tciUseStatus;
	}
	
	@Column(name = "tci_user_claass_id",  nullable = false, length=19)
	public Long getTciUserClaassId() {
		return this.tciUserClaassId;
	}
	
	public void setTciUserClaassId(Long tciUserClaassId) {
		this.tciUserClaassId = tciUserClaassId;
	}
	
	
}