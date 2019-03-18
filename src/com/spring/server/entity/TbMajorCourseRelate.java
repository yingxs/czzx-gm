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
 * 专业课程关联表控制器
 *
 * @author 
 * @Date 2019-03-12 11:23:00
 */
@Entity
@Table(name = "tb_major_course_relate")
public class TbMajorCourseRelate implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tcrrId;
	//专业id
	private Long tcrrMajorId;
	//课程id
	private Long tcrrCourseId;
	//添加时间
	private Timestamp tcrrAddTime;
	//添加人
	private Integer tcrrAddPerson;
	
	// Constructors

	/** default constructor */
	public TbMajorCourseRelate() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tcrr_id", unique = true, nullable = false, length=19)
	public Long getTcrrId() {
		return this.tcrrId;
	}
	
	public void setTcrrId(Long tcrrId) {
		this.tcrrId = tcrrId;
	}
	
	@Column(name = "tcrr_major_id",  nullable = false, length=19)
	public Long getTcrrMajorId() {
		return this.tcrrMajorId;
	}
	
	public void setTcrrMajorId(Long tcrrMajorId) {
		this.tcrrMajorId = tcrrMajorId;
	}
	
	@Column(name = "tcrr_course_id",  nullable = false, length=19)
	public Long getTcrrCourseId() {
		return this.tcrrCourseId;
	}
	
	public void setTcrrCourseId(Long tcrrCourseId) {
		this.tcrrCourseId = tcrrCourseId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tcrr_add_time",  nullable = false, length=19)
	public Timestamp getTcrrAddTime() {
		return this.tcrrAddTime;
	}
	
	public void setTcrrAddTime(Timestamp tcrrAddTime) {
		this.tcrrAddTime = tcrrAddTime;
	}
	
	@Column(name = "tcrr_add_person",  nullable = false, length=10)
	public Integer getTcrrAddPerson() {
		return this.tcrrAddPerson;
	}
	
	public void setTcrrAddPerson(Integer tcrrAddPerson) {
		this.tcrrAddPerson = tcrrAddPerson;
	}
	
	
}