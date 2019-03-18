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
 * 班级表控制器
 *
 * @author zhouyuan
 * @Date 2019-03-16 23:40:17
 */
@Entity
@Table(name = "tb_class")
public class TbClassInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号，主键
	private Long tcId;
	//年级
	private Long tcGrade;
	//班级名称
	private String tcName;
	// 添加时间	
	private Timestamp tcAddtime;
	//添加人	
	private Long tcAddperson;
	//更新人
	private Long tcUpdateperson;
	//更新时间
	private Timestamp tcUpdatetime;
	//状态 0:删除 1:正常
	private Integer tcStatus;
	//班主任
	private Long tcMainTeacherId;
	//班主任电话
	private String tcTeacherphoneId;
	//班级类型
	private Integer tcClasstype;
	//专业ID
	private Long tcMarjorId;
	//所属教室
	private Long tcClassroomId;
	//班级人数
	private Long tcClassperson;
	//所属学院
	private Long tcInstituteId;
	//所属系别
	private Long tcInstituteDepartmentId;
	
	// Constructors

	/** default constructor */
	public TbClassInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tc_id", unique = true, nullable = false, length=19)
	public Long getTcId() {
		return this.tcId;
	}
	
	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}
	
	@Column(name = "tc_grade",  nullable = false, length=19)
	public Long getTcGrade() {
		return this.tcGrade;
	}
	
	public void setTcGrade(Long tcGrade) {
		this.tcGrade = tcGrade;
	}
	
	@Column(name = "tc_name",  nullable = false, length=50)
	public String getTcName() {
		return this.tcName;
	}
	
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tc_addtime",  nullable = false, length=19)
	public Timestamp getTcAddtime() {
		return this.tcAddtime;
	}
	
	public void setTcAddtime(Timestamp tcAddtime) {
		this.tcAddtime = tcAddtime;
	}
	
	@Column(name = "tc_addperson",  nullable = false, length=19)
	public Long getTcAddperson() {
		return this.tcAddperson;
	}
	
	public void setTcAddperson(Long tcAddperson) {
		this.tcAddperson = tcAddperson;
	}
	
	@Column(name = "tc_updateperson",  nullable = false, length=19)
	public Long getTcUpdateperson() {
		return this.tcUpdateperson;
	}
	
	public void setTcUpdateperson(Long tcUpdateperson) {
		this.tcUpdateperson = tcUpdateperson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tc_updatetime",  nullable = false, length=19)
	public Timestamp getTcUpdatetime() {
		return this.tcUpdatetime;
	}
	
	public void setTcUpdatetime(Timestamp tcUpdatetime) {
		this.tcUpdatetime = tcUpdatetime;
	}
	
	@Column(name = "tc_status",  nullable = false, length=10)
	public Integer getTcStatus() {
		return this.tcStatus;
	}
	
	public void setTcStatus(Integer tcStatus) {
		this.tcStatus = tcStatus;
	}
	
	@Column(name = "tc_main_teacher_id",  nullable = false, length=19)
	public Long getTcMainTeacherId() {
		return this.tcMainTeacherId;
	}
	
	public void setTcMainTeacherId(Long tcMainTeacherId) {
		this.tcMainTeacherId = tcMainTeacherId;
	}
	
	@Column(name = "tc_teacherphone_id",  nullable = false, length=20)
	public String getTcTeacherphoneId() {
		return this.tcTeacherphoneId;
	}
	
	public void setTcTeacherphoneId(String tcTeacherphoneId) {
		this.tcTeacherphoneId = tcTeacherphoneId;
	}
	
	@Column(name = "tc_classtype",  nullable = false, length=10)
	public Integer getTcClasstype() {
		return this.tcClasstype;
	}
	
	public void setTcClasstype(Integer tcClasstype) {
		this.tcClasstype = tcClasstype;
	}
	
	@Column(name = "tc_marjor_id",  nullable = false, length=19)
	public Long getTcMarjorId() {
		return this.tcMarjorId;
	}
	
	public void setTcMarjorId(Long tcMarjorId) {
		this.tcMarjorId = tcMarjorId;
	}
	
	@Column(name = "tc_classroom_id",  nullable = false, length=19)
	public Long getTcClassroomId() {
		return this.tcClassroomId;
	}
	
	public void setTcClassroomId(Long tcClassroomId) {
		this.tcClassroomId = tcClassroomId;
	}
	
	@Column(name = "tc_classperson",  nullable = false, length=19)
	public Long getTcClassperson() {
		return this.tcClassperson;
	}
	
	public void setTcClassperson(Long tcClassperson) {
		this.tcClassperson = tcClassperson;
	}
	
	@Column(name = "tc_institute_id",  nullable = false, length=19)
	public Long getTcInstituteId() {
		return this.tcInstituteId;
	}
	
	public void setTcInstituteId(Long tcInstituteId) {
		this.tcInstituteId = tcInstituteId;
	}
	
	@Column(name = "tc_institute_department_id",  nullable = false, length=19)
	public Long getTcInstituteDepartmentId() {
		return this.tcInstituteDepartmentId;
	}
	
	public void setTcInstituteDepartmentId(Long tcInstituteDepartmentId) {
		this.tcInstituteDepartmentId = tcInstituteDepartmentId;
	}
	
	
}