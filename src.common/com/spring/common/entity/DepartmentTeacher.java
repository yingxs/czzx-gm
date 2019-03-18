/*package com.spring.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

*//**
 * DepartmentTeacher entity.
 *//*
@Entity
@Table(name = "tb_department_teacher")
public class DepartmentTeacher {
	private Integer id;
	private Integer departmentId;
	private Integer teacherId;
	private Date addDate;
	private Integer status;
	private String teacherDesp;
	private Integer position;

	public DepartmentTeacher( Integer departmentId, Integer teacherId, Date addDate, Integer status) {
		super();
		this.departmentId = departmentId;
		this.teacherId = teacherId;
		this.addDate = addDate;
		this.status = status;
	}

	public DepartmentTeacher() {
	}

	*//**
	 * @return the id
	 *//*
	@Id
	@GeneratedValue
	@Column(name = "tdt_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	*//**
	 * @param id
	 *            the id to set
	 *//*
	public void setId(Integer id) {
		this.id = id;
	}

	*//**
	 * @return the departmentId
	 *//*
	@Column(name = "tdt_department_id", nullable = false,length=8)
	public Integer getDepartmentId() {
		return departmentId;
	}

	*//**
	 * @param departmentId
	 *            the departmentId to set
	 *//*
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	*//**
	 * @return the teacherId
	 *//*
	@Column(name = "tdt_teacher_id", nullable = false,length=8)
	public Integer getTeacherId() {
		return teacherId;
	}

	*//**
	 * @param teacherId
	 *            the teacherId to set
	 *//*
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	*//**
	 * @return the addDate
	 *//*
	@Column(name = "tdt_add_date", nullable = false)
	public Date getAddDate() {
		return addDate;
	}

	*//**
	 * @param addDate
	 *            the addDate to set
	 *//*
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	*//**
	 * @return the status
	 *//*
	@Column(name = "tdt_status", nullable = false,length=4)
	public Integer getStatus() {
		return status;
	}

	*//**
	 * @param status
	 *            the status to set
	 *//*
	public void setStatus(Integer status) {
		this.status = status;
	}

	*//**
	 * @return the teacherDesp
	 *//*
	@Column(name = "tdt_teacher_desp")
	public String getTeacherDesp() {
		return teacherDesp;
	}

	*//**
	 * @param teacherDesp
	 *            the teacherDesp to set
	 *//*
	public void setTeacherDesp(String teacherDesp) {
		this.teacherDesp = teacherDesp;
	}

	*//**
	 * @return the position
	 *//*
	@Column(name = "tdt_position",length=20)
	public Integer getPosition() {
		return position;
	}

	*//**
	 * @param position
	 *            the position to set
	 *//*
	public void setPosition(Integer position) {
		this.position = position;
	}

}
*/