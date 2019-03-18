package com.spring.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  部门成员表entity
 *
 */
@Entity
@Table(name = "tb_department_teacher")
public class TbDepartmentTeacher {
	private Long tdtId;//部门成员表主键Id
	private Long tdtDepartmentId;//部门id
	private Long tdtTeacherId;//教师id
	private Timestamp tdtAddDate;//创建时间
	private Integer tdtStatus;//状态 1:正常 0:删除
	private String tdtTeacherDesp;//教师简介
	private Long tdtPosition;//岗位
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tdt_id")
	public Long getTdtId() {
		return tdtId;
	}
	public void setTdtId(Long tdtId) {
		this.tdtId = tdtId;
	}
	@Column(name = "tdt_department_id")
	public Long getTdtDepartmentId() {
		return tdtDepartmentId;
	}
	public void setTdtDepartmentId(Long tdtDepartmentId) {
		this.tdtDepartmentId = tdtDepartmentId;
	}
	@Column(name = "tdt_teacher_id")
	public Long getTdtTeacherId() {
		return tdtTeacherId;
	}
	public void setTdtTeacherId(Long tdtTeacherId) {
		this.tdtTeacherId = tdtTeacherId;
	}
	@Column(name = "tdt_add_date")
	public Timestamp getTdtAddDate() {
		return tdtAddDate;
	}
	public void setTdtAddDate(Timestamp tdtAddDate) {
		this.tdtAddDate = tdtAddDate;
	}
	@Column(name = "tdt_status")
	public Integer getTdtStatus() {
		return tdtStatus;
	}
	public void setTdtStatus(Integer tdtStatus) {
		this.tdtStatus = tdtStatus;
	}
	@Column(name = "tdt_teacher_desp")
	public String getTdtTeacherDesp() {
		return tdtTeacherDesp;
	}
	public void setTdtTeacherDesp(String tdtTeacherDesp) {
		this.tdtTeacherDesp = tdtTeacherDesp;
	}
	@Column(name = "tdt_position")
	public Long getTdtPosition() {
		return tdtPosition;
	}
	public void setTdtPosition(Long tdtPosition) {
		this.tdtPosition = tdtPosition;
	}
	public TbDepartmentTeacher(Long tdtId, Long tdtDepartmentId, Long tdtTeacherId, Timestamp tdtAddDate,
			Integer tdtStatus, String tdtTeacherDesp, Long tdtPosition) {
		super();
		this.tdtId = tdtId;
		this.tdtDepartmentId = tdtDepartmentId;
		this.tdtTeacherId = tdtTeacherId;
		this.tdtAddDate = tdtAddDate;
		this.tdtStatus = tdtStatus;
		this.tdtTeacherDesp = tdtTeacherDesp;
		this.tdtPosition = tdtPosition;
	}
	public TbDepartmentTeacher() {
		super();
	}
	
	

}
