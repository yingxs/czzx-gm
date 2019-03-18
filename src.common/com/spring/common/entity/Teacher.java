package com.spring.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Teacher entity.
 */
@Entity
@Table(name = "tb_teacher")
public class Teacher {
	private Integer id;
	private String name;
	private String phone;
	private Integer sex;
	private String birth;
	private Integer provinceId;
	private Integer cityId;
	private Integer regionId;
	private String address;
	private String courses;
	private Date addtime;
	private Long adduser;
	private String photo;
	private String cardNum;
	private String inDate;
	private String marriage;
	private String political;
	private String teacherNum;
	private String mail;
	private String loginUser;
	private String loginPass;
	private Integer status;
	private String number;
	private String contactPerson;
	private String contactPhone;

	private String ttClasss;

	private String ttGrade;

	/** 界面转化字段 **/
	private String sexName;

	/** full constructor */
	public Teacher(Integer id, String name, String phone, Integer sex,
			String birth, Integer provinceId, Integer cityId, Integer regionId,
			String address, String courses, Date addtime, Long adduser,
			String photo, String cardNum, String marriage, String political,
			String teacherNum, String mail, String loginUser, String loginPass,
			Integer status, String number, String contactPerson,
			String contactPhone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.sex = sex;
		this.birth = birth;
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.regionId = regionId;
		this.address = address;
		this.courses = courses;
		this.addtime = addtime;
		this.adduser = adduser;
		this.photo = photo;
		this.cardNum = cardNum;
		this.marriage = marriage;
		this.political = political;
		this.teacherNum = teacherNum;
		this.mail = mail;
		this.loginUser = loginUser;
		this.loginPass = loginPass;
		this.status = status;
		this.number = number;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
	}

	/** default constructor */
	public Teacher() {
	}

	@Column(name = "tt_classs")
	public String getTtClasss() {
		return ttClasss;
	}

	public void setTtClasss(String ttClasss) {
		this.ttClasss = ttClasss;
	}

	@Column(name = "tt_grade")
	public String getTtGrade() {
		return ttGrade;
	}

	public void setTtGrade(String ttGrade) {
		this.ttGrade = ttGrade;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name = "tt_id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@Column(name = "tt_name", length = 20)
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
	 * @return the phone
	 */
	@Column(name = "tt_phone", length = 21)
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the sex
	 */
	@Column(name = "tt_sex", length = 1)
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Transient
	public String getSexName() {
		if (this.getSex() == 1) {
			sexName = "男";
		} else if (this.getSex() == 2) {
			sexName = "女";
		}
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	/**
	 * @return the birth
	 */
	@Column(name = "tt_birth")
	public String getBirth() {
		return birth;
	}

	/**
	 * @param birth
	 *            the birth to set
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}

	/**
	 * @return the provinceId
	 */
	@Column(name = "tt_province_id", nullable = false, length = 4)
	public Integer getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cityId
	 */
	@Column(name = "tt_city_id", nullable = false, length = 4)
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the regionId
	 */
	@Column(name = "tt_region_id", nullable = false, length = 225)
	public Integer getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 *            the regionId to set
	 */
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return the address
	 */
	@Column(name = "tt_address", nullable = false, length = 225)
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the courses
	 */
	@Column(name = "tt_courses", length = 225)
	public String getCourses() {
		return courses;
	}

	/**
	 * @param courses
	 *            the courses to set
	 */
	public void setCourses(String courses) {
		this.courses = courses;
	}

	/**
	 * @return the addtime
	 */
	@Column(name = "tt_addtime", nullable = false)
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
	@Column(name = "tt_adduser", nullable = false, length = 8)
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
	 * @return the photo
	 */
	@Column(name = "tt_photo", nullable = false, length = 256)
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the cardNum
	 */
	@Column(name = "tt_card_num", nullable = false, length = 18)
	public String getCardNum() {
		return cardNum;
	}

	/**
	 * @param cardNum
	 *            the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	/**
	 * @return the inDate
	 */
	@Column(name = "tt_in_date", nullable = false, length = 10)
	public String getInDate() {
		return inDate;
	}

	/**
	 * @param inDate
	 *            the inDate to set
	 */
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	/**
	 * @return the marriage
	 */
	@Column(name = "tt_marriage", length = 20)
	public String getMarriage() {
		return marriage;
	}

	/**
	 * @param marriage
	 *            the marriage to set
	 */
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	/**
	 * @return the political
	 */
	@Column(name = "tt_political", length = 8)
	public String getPolitical() {
		return political;
	}

	/**
	 * @param political
	 *            the political to set
	 */
	public void setPolitical(String political) {
		this.political = political;
	}

	/**
	 * @return the teacherNum
	 */
	@Column(name = "tt_teacher_num", length = 20)
	public String getTeacherNum() {
		return teacherNum;
	}

	/**
	 * @param teacherNum
	 *            the teacherNum to set
	 */
	public void setTeacherNum(String teacherNum) {
		this.teacherNum = teacherNum;
	}

	/**
	 * @return the mail
	 */
	@Column(name = "tt_mail", nullable = false, length = 20)
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the loginUser
	 */
	@Column(name = "tt_login_user", length = 20)
	public String getLoginUser() {
		return loginUser;
	}

	/**
	 * @param loginUser
	 *            the loginUser to set
	 */
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	/**
	 * @return the loginPass
	 */
	@Column(name = "tt_login_pass", length = 20)
	public String getLoginPass() {
		return loginPass;
	}

	/**
	 * @param loginPass
	 *            the loginPass to set
	 */
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}

	/**
	 * @return the status
	 */
	@Column(name = "tt_status", length = 4)
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

	/**
	 * @return the number
	 */
	@Column(name = "tt_number", nullable = false, length = 10)
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the contactPerson
	 */
	@Column(name = "tt_contact_person", nullable = false, length = 10)
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactPhone
	 */
	@Column(name = "tt_contact_phone", nullable = false, length = 21)
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * @param contactPhone
	 *            the contactPhone to set
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}
