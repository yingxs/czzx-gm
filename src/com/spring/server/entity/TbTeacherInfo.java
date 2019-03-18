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
 * 教师表控制器
 *
 * @author 
 * @Date 2019-03-13 16:55:07
 */
@Entity
@Table(name = "tb_teacher_info")
public class TbTeacherInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tfiId;
	//教师姓名
	private String ttiName;
	//人员编码
	private String ttiNumber;
	//头像
	private String ttiPhoto;
	//性别（1:男 0:女）
	private String ttiSex;
	//身份证号
	private String ttiCardNum;
	//生日
	private Timestamp ttiBirthday;
	//手机号码
	private String ttiPhone;
	//民族tb_words（04）
	private Long ttiNation;
	//政治面貌tb_words（10）
	private Long ttiPolitical;
	//学历tb_words（06）
	private Long ttiInDegree;
	//毕业院校
	private String ttiSchool;
	//所属专业
	private String ttiMajor;
	//职称tb_words（14）
	private Long ttiPosition;
	//教职工类别tb_words（15）
	private Long ttiPositionType;
	//教授课程
	private String ttiCoursesId;
	//现任工作tb_words(14)
	private Long ttiPost;
	//登录账号
	private String ttiLoginUser;
	//登录密码
	private String ttiLoginPass;
	private String ttiLoginAganpass;
	//添加时间
	private Timestamp ttiAddtime;
	//添加人
	private Long ttiAdduser;
	//更新时间
	private Timestamp ttiUpdateDate;
	//更新人
	private Long ttiUpdatePerson;
	//状态
	private Integer ttiTatus;
	//积分
	private Long ttiScore;
	
	// Constructors

	/** default constructor */
	public TbTeacherInfo() {
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
	
	@Column(name = "tti_name",  nullable = false, length=20)
	public String getTtiName() {
		return this.ttiName;
	}
	
	public void setTtiName(String ttiName) {
		this.ttiName = ttiName;
	}
	
	@Column(name = "tti_number",  nullable = false, length=20)
	public String getTtiNumber() {
		return this.ttiNumber;
	}
	
	public void setTtiNumber(String ttiNumber) {
		this.ttiNumber = ttiNumber;
	}
	
	@Column(name = "tti_photo",  nullable = false, length=256)
	public String getTtiPhoto() {
		return this.ttiPhoto;
	}
	
	public void setTtiPhoto(String ttiPhoto) {
		this.ttiPhoto = ttiPhoto;
	}
	
	@Column(name = "tti_sex",  nullable = false, length=0)
	public String getTtiSex() {
		return this.ttiSex;
	}
	
	public void setTtiSex(String ttiSex) {
		this.ttiSex = ttiSex;
	}
	
	@Column(name = "tti_card_num",  nullable = false, length=18)
	public String getTtiCardNum() {
		return this.ttiCardNum;
	}
	
	public void setTtiCardNum(String ttiCardNum) {
		this.ttiCardNum = ttiCardNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tti_birthday",  nullable = false, length=10)
	public Timestamp getTtiBirthday() {
		return this.ttiBirthday;
	}
	
	public void setTtiBirthday(Timestamp ttiBirthday) {
		this.ttiBirthday = ttiBirthday;
	}
	
	@Column(name = "tti_phone",  nullable = false, length=256)
	public String getTtiPhone() {
		return this.ttiPhone;
	}
	
	public void setTtiPhone(String ttiPhone) {
		this.ttiPhone = ttiPhone;
	}
	
	@Column(name = "tti_nation",  nullable = false, length=19)
	public Long getTtiNation() {
		return this.ttiNation;
	}
	
	public void setTtiNation(Long ttiNation) {
		this.ttiNation = ttiNation;
	}
	
	@Column(name = "tti_political",  nullable = false, length=19)
	public Long getTtiPolitical() {
		return this.ttiPolitical;
	}
	
	public void setTtiPolitical(Long ttiPolitical) {
		this.ttiPolitical = ttiPolitical;
	}
	
	@Column(name = "tti_in_degree",  nullable = false, length=19)
	public Long getTtiInDegree() {
		return this.ttiInDegree;
	}
	
	public void setTtiInDegree(Long ttiInDegree) {
		this.ttiInDegree = ttiInDegree;
	}
	
	@Column(name = "tti_school",  nullable = false, length=50)
	public String getTtiSchool() {
		return this.ttiSchool;
	}
	
	public void setTtiSchool(String ttiSchool) {
		this.ttiSchool = ttiSchool;
	}
	
	@Column(name = "tti_major",  nullable = false, length=50)
	public String getTtiMajor() {
		return this.ttiMajor;
	}
	
	public void setTtiMajor(String ttiMajor) {
		this.ttiMajor = ttiMajor;
	}
	
	@Column(name = "tti_position",  nullable = false, length=19)
	public Long getTtiPosition() {
		return this.ttiPosition;
	}
	
	public void setTtiPosition(Long ttiPosition) {
		this.ttiPosition = ttiPosition;
	}
	
	@Column(name = "tti_position_type",  nullable = false, length=19)
	public Long getTtiPositionType() {
		return this.ttiPositionType;
	}
	
	public void setTtiPositionType(Long ttiPositionType) {
		this.ttiPositionType = ttiPositionType;
	}
	
	@Column(name = "tti_courses_id",  nullable = false, length=50)
	public String getTtiCoursesId() {
		return this.ttiCoursesId;
	}
	
	public void setTtiCoursesId(String ttiCoursesId) {
		this.ttiCoursesId = ttiCoursesId;
	}
	
	@Column(name = "tti_post",  nullable = false, length=19)
	public Long getTtiPost() {
		return this.ttiPost;
	}
	
	public void setTtiPost(Long ttiPost) {
		this.ttiPost = ttiPost;
	}
	
	@Column(name = "tti_login_user",  nullable = false, length=20)
	public String getTtiLoginUser() {
		return this.ttiLoginUser;
	}
	
	public void setTtiLoginUser(String ttiLoginUser) {
		this.ttiLoginUser = ttiLoginUser;
	}
	
	@Column(name = "tti_login_pass",  nullable = false, length=50)
	public String getTtiLoginPass() {
		return this.ttiLoginPass;
	}
	
	public void setTtiLoginPass(String ttiLoginPass) {
		this.ttiLoginPass = ttiLoginPass;
	}
	
	@Column(name = "tti_login_aganpass",  nullable = false, length=50)
	public String getTtiLoginAganpass() {
		return this.ttiLoginAganpass;
	}
	
	public void setTtiLoginAganpass(String ttiLoginAganpass) {
		this.ttiLoginAganpass = ttiLoginAganpass;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tti_addtime",  nullable = false, length=19)
	public Timestamp getTtiAddtime() {
		return this.ttiAddtime;
	}
	
	public void setTtiAddtime(Timestamp ttiAddtime) {
		this.ttiAddtime = ttiAddtime;
	}
	
	@Column(name = "tti_adduser",  nullable = false, length=19)
	public Long getTtiAdduser() {
		return this.ttiAdduser;
	}
	
	public void setTtiAdduser(Long ttiAdduser) {
		this.ttiAdduser = ttiAdduser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tti_update_date",  nullable = false, length=19)
	public Timestamp getTtiUpdateDate() {
		return this.ttiUpdateDate;
	}
	
	public void setTtiUpdateDate(Timestamp ttiUpdateDate) {
		this.ttiUpdateDate = ttiUpdateDate;
	}
	
	@Column(name = "tti_update_person",  nullable = false, length=19)
	public Long getTtiUpdatePerson() {
		return this.ttiUpdatePerson;
	}
	
	public void setTtiUpdatePerson(Long ttiUpdatePerson) {
		this.ttiUpdatePerson = ttiUpdatePerson;
	}
	
	@Column(name = "tti_tatus",  nullable = false, length=10)
	public Integer getTtiTatus() {
		return this.ttiTatus;
	}
	
	public void setTtiTatus(Integer ttiTatus) {
		this.ttiTatus = ttiTatus;
	}
	
	@Column(name = "tti_score",  nullable = false, length=19)
	public Long getTtiScore() {
		return this.ttiScore;
	}
	
	public void setTtiScore(Long ttiScore) {
		this.ttiScore = ttiScore;
	}
	
	
}