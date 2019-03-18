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
 * 学生表控制器
 *
 * @author zhouyuan
 * @Date 2019-03-11 11:45:49
 */
@Entity
@Table(name = "tb_student")
public class TbStudent implements java.io.Serializable {

	// Fields

	// 所属专业
	private Long tsMajorId;
	// 所属年级
	private Long tsGrade;
	// 确认密码
	private String tsAganPass;
	// 备注
	private String tsRemarks;

	// 学院id ts_institute_id
	private Long tsInstituteId;
	// 系id  ts_institute_department_id
	private Long tsInstituteDepartmentId;
	// 序列号
	private Long tsId;
	// 姓名
	private String tsName;
	// 学号
	private String tsNumber;
	// 身份证号码
	private String tsCardNum;
	// 所属省
	private Long tsProvinceId;
	// 所属市
	private Long tsCityId;
	// 所属区
	private Long tsRegionId;
	// 详细地址
	private String tsAddress;
	// 添加时间
	private Timestamp tsAddtime;
	// 添加人
	private Long tsAdduser;
	// 学生状态
	// tb_words（02）
	private Integer tsStudentStatus;
	// 出生日期
	private String tsBirthday;
	// 民族
	// tb_words（09）
	private Long tsNation;
	// 政治面貌
	// tb_words（10）
	private Long tsPolitical;
	// 籍贯
	private String tsJiguan;
	// 登录账号
	private String tsLoginUser;
	// 登录密码
	private String tsLoginPass;
	// 班级id
	// tb_class
	private Long tsClassId;
	// 家庭收入来源
	// tb_words（11）
	private Long tsFamilyIncome;
	// 学历
	// tb_words（06）
	private Long tsInDegree;
	// 户口性质
	// tb_words（07）
	private Long tsHjProperty;
	// 更新时间
	private Timestamp tsUpdateDate;
	// 更新人
	private Long tsUpdatePerson;
	// 头像
	private String tsPhoto;
	// 性别1:男 0:女
	private String tsSex;
	// 联系号码
	private String tsPhone;
	// 状态 0：删除 1：正常
	private Integer tsStatus;

	// Constructors

	
	
	@Column(name = "ts_institute_id", nullable = false, length = 19)
	public Long getTsInstituteId() {
		return tsInstituteId;
	}

	

	
	
	public TbStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setTsInstituteId(Long tsInstituteId) {
		this.tsInstituteId = tsInstituteId;
	}

	@Column(name = "ts_institute_department_id", nullable = false, length = 19)
	public Long getTsInstituteDepartmentId() {
		return tsInstituteDepartmentId;
	}

	public void setTsInstituteDepartmentId(Long tsInstituteDepartmentId) {
		this.tsInstituteDepartmentId = tsInstituteDepartmentId;
	}

	@Column(name = "ts_major_id", nullable = false, length = 19)
	public Long getTsMajorId() {
		return tsMajorId;
	}

	public void setTsMajorId(Long tsMajorId) {
		this.tsMajorId = tsMajorId;
	}

	@Column(name = "ts_grade", nullable = false, length = 19)
	public Long getTsGrade() {
		return tsGrade;
	}

	public void setTsGrade(Long tsGrade) {
		this.tsGrade = tsGrade;
	}

	@Column(name = "ts_agan_pass", nullable = false, length = 20)
	public String getTsAganPass() {
		return tsAganPass;
	}

	public void setTsAganPass(String tsAganPass) {
		this.tsAganPass = tsAganPass;
	}

	@Column(name = "ts_remarks", nullable = false, length = 50)
	public String getTsRemarks() {
		return tsRemarks;
	}

	public void setTsRemarks(String tsRemarks) {
		this.tsRemarks = tsRemarks;
	}

	

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ts_id", unique = true, nullable = false, length = 19)
	public Long getTsId() {
		return this.tsId;
	}

	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}

	@Column(name = "ts_name", nullable = false, length = 20)
	public String getTsName() {
		return this.tsName;
	}

	public void setTsName(String tsName) {
		this.tsName = tsName;
	}

	@Column(name = "ts_number", nullable = false, length = 20)
	public String getTsNumber() {
		return this.tsNumber;
	}

	public void setTsNumber(String tsNumber) {
		this.tsNumber = tsNumber;
	}

	@Column(name = "ts_card_num", nullable = false, length = 18)
	public String getTsCardNum() {
		return this.tsCardNum;
	}

	public void setTsCardNum(String tsCardNum) {
		this.tsCardNum = tsCardNum;
	}

	@Column(name = "ts_province_id", nullable = false, length = 19)
	public Long getTsProvinceId() {
		return this.tsProvinceId;
	}

	public void setTsProvinceId(Long tsProvinceId) {
		this.tsProvinceId = tsProvinceId;
	}

	@Column(name = "ts_city_id", nullable = false, length = 19)
	public Long getTsCityId() {
		return this.tsCityId;
	}

	public void setTsCityId(Long tsCityId) {
		this.tsCityId = tsCityId;
	}

	@Column(name = "ts_region_id", nullable = false, length = 19)
	public Long getTsRegionId() {
		return this.tsRegionId;
	}

	public void setTsRegionId(Long tsRegionId) {
		this.tsRegionId = tsRegionId;
	}

	@Column(name = "ts_address", nullable = false, length = 256)
	public String getTsAddress() {
		return this.tsAddress;
	}

	public void setTsAddress(String tsAddress) {
		this.tsAddress = tsAddress;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "ts_addtime", nullable = false, length = 19)
	public Timestamp getTsAddtime() {
		return this.tsAddtime;
	}

	public void setTsAddtime(Timestamp tsAddtime) {
		this.tsAddtime = tsAddtime;
	}

	@Column(name = "ts_adduser", nullable = false, length = 19)
	public Long getTsAdduser() {
		return this.tsAdduser;
	}

	public void setTsAdduser(Long tsAdduser) {
		this.tsAdduser = tsAdduser;
	}

	@Column(name = "ts_student_status", nullable = false, length = 10)
	public Integer getTsStudentStatus() {
		return this.tsStudentStatus;
	}

	public void setTsStudentStatus(Integer tsStudentStatus) {
		this.tsStudentStatus = tsStudentStatus;
	}

	@Column(name = "ts_birthday", nullable = false, length = 10)
	public String getTsBirthday() {
		return this.tsBirthday;
	}

	public void setTsBirthday(String tsBirthday) {
		this.tsBirthday = tsBirthday;
	}

	@Column(name = "ts_nation", nullable = false, length = 19)
	public Long getTsNation() {
		return this.tsNation;
	}

	public void setTsNation(Long tsNation) {
		this.tsNation = tsNation;
	}

	@Column(name = "ts_political", nullable = false, length = 19)
	public Long getTsPolitical() {
		return this.tsPolitical;
	}

	public void setTsPolitical(Long tsPolitical) {
		this.tsPolitical = tsPolitical;
	}

	@Column(name = "ts_jiguan", nullable = false, length = 20)
	public String getTsJiguan() {
		return this.tsJiguan;
	}

	public void setTsJiguan(String tsJiguan) {
		this.tsJiguan = tsJiguan;
	}

	@Column(name = "ts_login_user", nullable = false, length = 20)
	public String getTsLoginUser() {
		return this.tsLoginUser;
	}

	public void setTsLoginUser(String tsLoginUser) {
		this.tsLoginUser = tsLoginUser;
	}

	@Column(name = "ts_login_pass", nullable = false, length = 50)
	public String getTsLoginPass() {
		return this.tsLoginPass;
	}

	public void setTsLoginPass(String tsLoginPass) {
		this.tsLoginPass = tsLoginPass;
	}

	@Column(name = "ts_class_id", nullable = false, length = 19)
	public Long getTsClassId() {
		return this.tsClassId;
	}

	public void setTsClassId(Long tsClassId) {
		this.tsClassId = tsClassId;
	}

	@Column(name = "ts_family_income", nullable = false, length = 19)
	public Long getTsFamilyIncome() {
		return this.tsFamilyIncome;
	}

	public void setTsFamilyIncome(Long tsFamilyIncome) {
		this.tsFamilyIncome = tsFamilyIncome;
	}

	@Column(name = "ts_in_degree", nullable = false, length = 19)
	public Long getTsInDegree() {
		return this.tsInDegree;
	}

	public void setTsInDegree(Long tsInDegree) {
		this.tsInDegree = tsInDegree;
	}

	@Column(name = "ts_hj_property", nullable = false, length = 19)
	public Long getTsHjProperty() {
		return this.tsHjProperty;
	}

	public void setTsHjProperty(Long tsHjProperty) {
		this.tsHjProperty = tsHjProperty;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "ts_update_date", nullable = false, length = 19)
	public Timestamp getTsUpdateDate() {
		return this.tsUpdateDate;
	}

	public void setTsUpdateDate(Timestamp tsUpdateDate) {
		this.tsUpdateDate = tsUpdateDate;
	}

	@Column(name = "ts_update_person", nullable = false, length = 19)
	public Long getTsUpdatePerson() {
		return this.tsUpdatePerson;
	}

	public void setTsUpdatePerson(Long tsUpdatePerson) {
		this.tsUpdatePerson = tsUpdatePerson;
	}

	@Column(name = "ts_photo", nullable = false, length = 256)
	public String getTsPhoto() {
		return this.tsPhoto;
	}

	public void setTsPhoto(String tsPhoto) {
		this.tsPhoto = tsPhoto;
	}

	@Column(name = "ts_sex", nullable = false, length = 0)
	public String getTsSex() {
		return this.tsSex;
	}

	public void setTsSex(String tsSex) {
		this.tsSex = tsSex;
	}

	@Column(name = "ts_phone", nullable = false, length = 256)
	public String getTsPhone() {
		return this.tsPhone;
	}

	public void setTsPhone(String tsPhone) {
		this.tsPhone = tsPhone;
	}

	@Column(name = "ts_status", nullable = false, length = 10)
	public Integer getTsStatus() {
		return this.tsStatus;
	}

	public void setTsStatus(Integer tsStatus) {
		this.tsStatus = tsStatus;
	}

}
