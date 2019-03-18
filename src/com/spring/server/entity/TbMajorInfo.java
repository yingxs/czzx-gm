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
 * 专业信息表控制器
 *
 * @author 
 * @Date 2019-03-07 14:54:30
 */
@Entity
@Table(name = "tb_major_info")
public class TbMajorInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tmiId;
	//专业名称
	private String tmiName;
	//所属系


	private Long tmiDepartmentId;
	//添加时间
	private Timestamp tmiAddDate;
	//添加人
	private Long tmiAddPerson;
	//状态1：正常 0: 删除
	

	private Integer tmiStatus;
	//专业代码
	private String tmiCode;
	//所属学院id
	private Long tmiInstituteId;
	//专业年限tb_words  该专业上几年
	

	private Long tmiMajorYear;
	
	// Constructors

	/** default constructor */
	public TbMajorInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tmi_id", unique = true, nullable = false, length=19)
	public Long getTmiId() {
		return this.tmiId;
	}
	
	public void setTmiId(Long tmiId) {
		this.tmiId = tmiId;
	}
	
	@Column(name = "tmi_name",  nullable = false, length=20)
	public String getTmiName() {
		return this.tmiName;
	}
	
	public void setTmiName(String tmiName) {
		this.tmiName = tmiName;
	}
	
	@Column(name = "tmi_department_id",  nullable = false, length=19)
	public Long getTmiDepartmentId() {
		return this.tmiDepartmentId;
	}
	
	public void setTmiDepartmentId(Long tmiDepartmentId) {
		this.tmiDepartmentId = tmiDepartmentId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tmi_add_date",  nullable = false, length=19)
	public Timestamp getTmiAddDate() {
		return this.tmiAddDate;
	}
	
	public void setTmiAddDate(Timestamp tmiAddDate) {
		this.tmiAddDate = tmiAddDate;
	}
	
	@Column(name = "tmi_add_person",  nullable = false, length=19)
	public Long getTmiAddPerson() {
		return this.tmiAddPerson;
	}
	
	public void setTmiAddPerson(Long tmiAddPerson) {
		this.tmiAddPerson = tmiAddPerson;
	}
	
	@Column(name = "tmi_status",  nullable = false, length=10)
	public Integer getTmiStatus() {
		return this.tmiStatus;
	}
	
	public void setTmiStatus(Integer tmiStatus) {
		this.tmiStatus = tmiStatus;
	}
	
	@Column(name = "tmi_code",  nullable = false, length=20)
	public String getTmiCode() {
		return this.tmiCode;
	}
	
	public void setTmiCode(String tmiCode) {
		this.tmiCode = tmiCode;
	}
	
	@Column(name = "tmi_institute_id",  nullable = false, length=19)
	public Long getTmiInstituteId() {
		return this.tmiInstituteId;
	}
	
	public void setTmiInstituteId(Long tmiInstituteId) {
		this.tmiInstituteId = tmiInstituteId;
	}
	
	@Column(name = "tmi_major_year",  nullable = false, length=19)
	public Long getTmiMajorYear() {
		return this.tmiMajorYear;
	}
	
	public void setTmiMajorYear(Long tmiMajorYear) {
		this.tmiMajorYear = tmiMajorYear;
	}
	
	
}