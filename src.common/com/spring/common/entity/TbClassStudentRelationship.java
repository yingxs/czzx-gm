package com.spring.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_class_student_relationship")
public class TbClassStudentRelationship implements Serializable{
	
	private Long tcsrId;
	private Long tcsrClassId;
	private Timestamp tcsrAddDate;
	private Integer tcsrStatus;
	private Long tcsrStudentId;
	private String tscrTeacherName;
	private Long tscrTeacherId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="tcsr_id",unique=true,nullable=false)
	public Long getTcsrId() {
		return tcsrId;
	}
	public void setTcsrId(Long tcsrId) {
		this.tcsrId = tcsrId;
	}
	@Column(name="tcsr_class_id")
	public Long getTcsrClassId() {
		return tcsrClassId;
	}
	public void setTcsrClassId(Long tcsrClassId) {
		this.tcsrClassId = tcsrClassId;
	}
	@Column(name="tcsr_add_date")
	public Timestamp getTcsrAddDate() {
		return tcsrAddDate;
	}
	public void setTcsrAddDate(Timestamp tcsrAddDate) {
		this.tcsrAddDate = tcsrAddDate;
	}
	@Column(name="tcsr_status")
	public Integer getTcsrStatus() {
		return tcsrStatus;
	}
	public void setTcsrStatus(Integer tcsrStatus) {
		this.tcsrStatus = tcsrStatus;
	}
	@Column(name="tcsr_student_id")
	public Long getTcsrStudentId() {
		return tcsrStudentId;
	}
	public void setTcsrStudentId(Long tcsrStudentId) {
		this.tcsrStudentId = tcsrStudentId;
	}
	@Column(name="tscr_teacher_name")
	public String getTscrTeacherName() {
		return tscrTeacherName;
	}
	public void setTscrTeacherName(String tscrTeacherName) {
		this.tscrTeacherName = tscrTeacherName;
	}
	@Column(name="tscr_teacher_id")
	public Long getTscrTeacherId() {
		return tscrTeacherId;
	}
	public void setTscrTeacherId(Long tscrTeacherId) {
		this.tscrTeacherId = tscrTeacherId;
	}
	
	public TbClassStudentRelationship(Long tcsrId, Long tcsrClassId,
			Timestamp tcsrAddDate, Integer tcsrStatus, Long tcsrStudentId,
			String tscrTeacherName, Long tscrTeacherId) {
		super();
		this.tcsrId = tcsrId;
		this.tcsrClassId = tcsrClassId;
		this.tcsrAddDate = tcsrAddDate;
		this.tcsrStatus = tcsrStatus;
		this.tcsrStudentId = tcsrStudentId;
		this.tscrTeacherName = tscrTeacherName;
		this.tscrTeacherId = tscrTeacherId;
	}
	
	public TbClassStudentRelationship() {
		super();
	}
	
}
