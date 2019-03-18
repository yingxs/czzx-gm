package com.spring.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Classs entity.
 */
@Entity
@Table(name = "tb_classs")
public class TbClass {

	private Long id;
	private Long gradeId;
	private String name;
	private Long teacherId;
	private Date addtime;
	private Long adduser;
	private Integer status;
	private String gradeStr;
	private String teacherName;
	private String teacherPhone;
	private String teacherAddress;

	private String gradeName;

	@Transient
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	/** default constructor */
	public TbClass() {
	}

	/** full constructor */
	public TbClass(Long id, Long gradeId, String name, Long teacherId,
			Date addtime, Long adduser, Integer status) {
		super();
		this.id = id;
		this.gradeId = gradeId;
		this.name = name;
		this.teacherId = teacherId;
		this.addtime = addtime;
		this.adduser = adduser;
		this.status = status;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tc_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the gradeId
	 */
	@Column(name = "tc_grade_id", length = 8)
	public Long getGradeId() {
		return gradeId;
	}

	/**
	 * @param gradeId
	 *            the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	/**
	 * @return the name
	 */
	@Column(name = "tc_name", length = 50)
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the teacherId
	 */
	@Column(name = "tc_teacher_id", length = 8)
	public Long getTeacherId() {
		return teacherId;
	}

	/**
	 * @param teacherId
	 *            the teacherId to set
	 */
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * @return the addtime
	 */
	@Column(name = "tc_addtime")
	public Date getAddtime() {
		return addtime;
	}

	/**
	 * @param addtime
	 *            the addtime to set
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	/**
	 * @return the adduser
	 */
	@Column(name = "tc_adduser", length = 8)
	public Long getAdduser() {
		return adduser;
	}

	/**
	 * @param adduser
	 *            the adduser to set
	 */
	public void setAdduser(Long adduser) {
		this.adduser = adduser;
	}

	/**
	 * @return the status
	 */
	@Column(name = "tc_status", nullable = false, length = 4)
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getGradeStr() {
		return gradeStr;
	}

	public void setGradeStr(String gradeStr) {
		this.gradeStr = gradeStr;
	}

	@Transient
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	@Transient
	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	@Transient
	public String getTeacherAddress() {
		return teacherAddress;
	}

	public void setTeacherAddress(String teacherAddress) {
		this.teacherAddress = teacherAddress;
	}

}
