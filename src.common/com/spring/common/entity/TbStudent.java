package com.spring.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_student")
public class TbStudent implements Serializable {


	private TbStudentFamily tbStudentFamily; // 学生家庭信息

	private TbClass tbClass; // 班级信息

	private TbGrade tbGrade; // 年级

	private Teacher teacher; // 教师

	private Long tsId;

	private String tsNumber;

	private String tsCardNum;

	private Long tsProvinceId;

	private Long tsCityId;

	private Long tsCountyId;

	private String tsAddress;

	private Date tsAddtime;

	private Long tsAdduser;

	private Integer tsStatus;

	private Long tsAccountId;

	private String tsBirthday;

	private String tsNation;

	private String tsPolitical;

	private String tsJiguan;

	private String tsLoginUser;

	private String tsLoginPass;

	private Integer tsClassId;

	private Integer tsSex;

	private String tsPhoto;
	
	private String tsName;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ts_id", nullable = false, unique = true )
	public Long getTsId() {
		return tsId;
	}
	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}
	@Column(name="ts_number")
	public String getTsNumber() {
		return tsNumber;
	}
	public void setTsNumber(String tsNumber) {
		this.tsNumber = tsNumber;
	}
	@Column(name="ts_card_num")
	public String getTsCardNum() {
		return tsCardNum;
	}
	public void setTsCardNum(String tsCardNum) {
		this.tsCardNum = tsCardNum;
	}
	@Column(name="ts_province_id")
	public Long getTsProvinceId() {
		return tsProvinceId;
	}
	public void setTsProvinceId(Long tsProvinceId) {
		this.tsProvinceId = tsProvinceId;
	}
	@Column(name="ts_city_id")
	public Long getTsCityId() {
		return tsCityId;
	}
	public void setTsCityId(Long tsCityId) {
		this.tsCityId = tsCityId;
	}
	@Column(name="ts_county_id")
	public Long getTsCountyId() {
		return tsCountyId;
	}
	public void setTsCountyId(Long tsCountyId) {
		this.tsCountyId = tsCountyId;
	}
	@Column(name="ts_address")
	public String getTsAddress() {
		return tsAddress;
	}
	public void setTsAddress(String tsAddress) {
		this.tsAddress = tsAddress;
	}
	@Column(name="ts_addtime")
	public Date getTsAddtime() {
		return tsAddtime;
	}
	public void setTsAddtime(Date tsAddtime) {
		this.tsAddtime = tsAddtime;
	}
	@Column(name="ts_adduser")
	public Long getTsAdduser() {
		return tsAdduser;
	}
	public void setTsAdduser(Long tsAdduser) {
		this.tsAdduser = tsAdduser;
	}
	@Column(name="ts_status")
	public Integer getTsStatus() {
		return tsStatus;
	}
	public void setTsStatus(Integer tsStatus) {
		this.tsStatus = tsStatus;
	}
	@Column(name="ts_account_id")
	public Long getTsAccountId() {
		return tsAccountId;
	}
	public void setTsAccountId(Long tsAccountId) {
		this.tsAccountId = tsAccountId;
	}
	@Column(name="ts_birthday")
	public String getTsBirthday() {
		return tsBirthday;
	}
	public void setTsBirthday(String tsBirthday) {
		this.tsBirthday = tsBirthday;
	}
	@Column(name="ts_nation")
	public String getTsNation() {
		return tsNation;
	}
	public void setTsNation(String tsNation) {
		this.tsNation = tsNation;
	}
	@Column(name="ts_political")
	public String getTsPolitical() {
		return tsPolitical;
	}
	public void setTsPolitical(String tsPolitical) {
		this.tsPolitical = tsPolitical;
	}
	@Column(name="ts_jiguan")
	public String getTsJiguan() {
		return tsJiguan;
	}
	public void setTsJiguan(String tsJiguan) {
		this.tsJiguan = tsJiguan;
	}
	@Column(name="ts_login_user")
	public String getTsLoginUser() {
		return tsLoginUser;
	}
	public void setTsLoginUser(String tsLoginUser) {
		this.tsLoginUser = tsLoginUser;
	}
	@Column(name="ts_login_pass")
	public String getTsLoginPass() {
		return tsLoginPass;
	}
	public void setTsLoginPass(String tsLoginPass) {
		this.tsLoginPass = tsLoginPass;
	}
	@Column(name="ts_class_id")
	public Integer getTsClassId() {
		return tsClassId;
	}
	public void setTsClassId(Integer tsClassId) {
		this.tsClassId = tsClassId;
	}
	@Column(name="ts_sex")
	public Integer getTsSex() {
		return tsSex;
	}
	public void setTsSex(Integer tsSex) {
		this.tsSex = tsSex;
	}
	@Column(name="ts_name")
	public String getTsName() {
		return tsName;
	}
	public void setTsName(String tsName) {
		this.tsName = tsName;
	}
	@Transient
	public TbStudentFamily getTbStudentFamily() {
		return tbStudentFamily;
	}

	public void setTbStudentFamily(TbStudentFamily tbStudentFamily) {
		this.tbStudentFamily = tbStudentFamily;
	}
	@Transient
	public String getTsPhoto() {
		return tsPhoto;
	}
	public void setTsPhoto(String tsPhoto) {
		this.tsPhoto = tsPhoto;
	}
	@Transient
	public TbClass getTbClass() {
		return tbClass;
	}

	public void setTbClass(TbClass tbClass) {
		this.tbClass = tbClass;
	}

	@Transient
	public TbGrade getTbGrade() {
		return tbGrade;
	}

	public void setTbGrade(TbGrade tbGrade) {
		this.tbGrade = tbGrade;
	}

	@Transient
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public TbStudent() {
		super();
	}
	

	public TbStudent(TbStudentFamily tbStudentFamily, TbClass tbClass,
			TbGrade tbGrade, Teacher teacher, Long tsId, 
			String tsNumber, String tsCardNum, Long tsProvinceId,
			Long tsCityId, Long tsCountyId, String tsAddress, Date tsAddtime,
			Long tsAdduser, Integer tsStatus, Long tsAccountId,
			String tsBirthday, String tsNation, String tsPolitical,
			String tsJiguan, String tsLoginUser, String tsLoginPass,
			Integer tsClassId, Integer tsSex, String tsPhoto, String tsName) {
		super();
		this.tbStudentFamily = tbStudentFamily;
		this.tbClass = tbClass;
		this.tbGrade = tbGrade;
		this.teacher = teacher;
		this.tsId = tsId;
		this.tsNumber = tsNumber;
		this.tsCardNum = tsCardNum;
		this.tsProvinceId = tsProvinceId;
		this.tsCityId = tsCityId;
		this.tsCountyId = tsCountyId;
		this.tsAddress = tsAddress;
		this.tsAddtime = tsAddtime;
		this.tsAdduser = tsAdduser;
		this.tsStatus = tsStatus;
		this.tsAccountId = tsAccountId;
		this.tsBirthday = tsBirthday;
		this.tsNation = tsNation;
		this.tsPolitical = tsPolitical;
		this.tsJiguan = tsJiguan;
		this.tsLoginUser = tsLoginUser;
		this.tsLoginPass = tsLoginPass;
		this.tsClassId = tsClassId;
		this.tsSex = tsSex;
		this.tsPhoto = tsPhoto;
		this.tsName = tsName;
	}

	@Override
	public String toString() {
		return "TbStudent [tsId=" + tsId + ", tsNumber=" + tsNumber + ", tsCard_num=" + tsCardNum + ", tsProvinceId=" + tsProvinceId + ", tsCityId=" + tsCityId + ", tsCountyId=" + tsCountyId + ", tsAddress=" + tsAddress + ", tsAddtime=" + tsAddtime + ", tsAdduser=" + tsAdduser + ", tsStatus=" + tsStatus + ", tsAccountId=" + tsAccountId + ", tsBirthday=" + tsBirthday + ", tsNation=" + tsNation + ", tsPolitical=" + tsPolitical + ", tsJiguan=" + tsJiguan
				+ ", tsLoginUser=" + tsLoginUser + ", tsLoginPass=" + tsLoginPass + ", tsClassId=" + tsClassId + ", tsName=" + tsName + "]";
	}

}
