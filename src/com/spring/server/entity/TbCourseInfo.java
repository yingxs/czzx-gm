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
 * 课程信息表控制器
 *
 * @author
 * @Date 2019-03-08 11:38:49
 */
@Entity
@Table(name = "tb_course_info")
public class TbCourseInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tciId;
	//课程名称
	private String tciName;
	//课程代码
	private String tciCode;
	//学分
	private Integer tciScore;
	//课程目标
	private String tciTaget;
	//课程计划
	private String tciPlan;
	//课程要求
	private String tciNeed;
	//课程内容
	private String tciContent;
	//所属专业
	//private Long tciMajorsId;
	//所属科目
	private Long tciSubjectsId;
	//状态1:正常   0:删除
	 

	private Integer tciStatus;
	//创建日期
	private Timestamp tciAddTime;
	//创建人
	private Long tciAddPerson;
	
	// Constructors

	/** default constructor */
	public TbCourseInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tci_id", unique = true, nullable = false, length=19)
	public Long getTciId() {
		return this.tciId;
	}
	
	public void setTciId(Long tciId) {
		this.tciId = tciId;
	}
	
	@Column(name = "tci_name",  nullable = false, length=30)
	public String getTciName() {
		return this.tciName;
	}
	
	public void setTciName(String tciName) {
		this.tciName = tciName;
	}
	
	@Column(name = "tci_code",  nullable = false, length=20)
	public String getTciCode() {
		return this.tciCode;
	}
	
	public void setTciCode(String tciCode) {
		this.tciCode = tciCode;
	}
	
	@Column(name = "tci_score",  nullable = false, length=10)
	public Integer getTciScore() {
		return this.tciScore;
	}
	
	public void setTciScore(Integer tciScore) {
		this.tciScore = tciScore;
	}
	
	@Column(name = "tci_taget",  nullable = false, length=65535)
	public String getTciTaget() {
		return this.tciTaget;
	}
	
	public void setTciTaget(String tciTaget) {
		this.tciTaget = tciTaget;
	}
	
	@Column(name = "tci_plan",  nullable = false, length=65535)
	public String getTciPlan() {
		return this.tciPlan;
	}
	
	public void setTciPlan(String tciPlan) {
		this.tciPlan = tciPlan;
	}
	
	@Column(name = "tci_need",  nullable = false, length=65535)
	public String getTciNeed() {
		return this.tciNeed;
	}
	
	public void setTciNeed(String tciNeed) {
		this.tciNeed = tciNeed;
	}
	
	@Column(name = "tci_content",  nullable = false, length=65535)
	public String getTciContent() {
		return this.tciContent;
	}
	
	public void setTciContent(String tciContent) {
		this.tciContent = tciContent;
	}
	
	/*@Column(name = "tci_majors_id",  nullable = false, length=19)
	public Long getTciMajorsId() {
		return this.tciMajorsId;
	}
	
	public void setTciMajorsId(Long tciMajorsId) {
		this.tciMajorsId = tciMajorsId;
	}*/
	
	@Column(name = "tci_subjects_id",  nullable = false, length=19)
	public Long getTciSubjectsId() {
		return this.tciSubjectsId;
	}
	
	public void setTciSubjectsId(Long tciSubjectsId) {
		this.tciSubjectsId = tciSubjectsId;
	}
	
	@Column(name = "tci_status",  nullable = false, length=10)
	public Integer getTciStatus() {
		return this.tciStatus;
	}
	
	public void setTciStatus(Integer tciStatus) {
		this.tciStatus = tciStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tci_add_time",  nullable = false, length=19)
	public Timestamp getTciAddTime() {
		return this.tciAddTime;
	}
	
	public void setTciAddTime(Timestamp tciAddTime) {
		this.tciAddTime = tciAddTime;
	}
	
	@Column(name = "tci_add_person",  nullable = false, length=19)
	public Long getTciAddPerson() {
		return this.tciAddPerson;
	}
	
	public void setTciAddPerson(Long tciAddPerson) {
		this.tciAddPerson = tciAddPerson;
	}

	@Override
	public String toString() {
		return "TbcourseInfo{" +
				"tciId=" + tciId +
				", tciName='" + tciName + '\'' +
				", tciCode='" + tciCode + '\'' +
				", tciScore=" + tciScore +
				", tciTaget='" + tciTaget + '\'' +
				", tciPlan='" + tciPlan + '\'' +
				", tciNeed='" + tciNeed + '\'' +
				", tciContent='" + tciContent + '\'' +
				/*", tciMajorsId=" + tciMajorsId +*/
				", tciSubjectsId=" + tciSubjectsId +
				", tciStatus=" + tciStatus +
				", tciAddTime=" + tciAddTime +
				", tciAddPerson=" + tciAddPerson +
				'}';
	}
}