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
 * 科目表控制器
 *
 * @author 
 * @Date 2019-03-08 16:14:27
 */
@Entity
@Table(name = "tb_subjects_info")
public class TbSubjectsInfo implements java.io.Serializable {
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//序列号
	private Long tsiId;
	//科目名称
	private String tsiName;
	//添加时间
	private Timestamp tsiAddDate;
	//添加人
	private String tsiAddPerson;
	//状态  1.正常 0.删除

	private Integer tsiStatus;
	
	// Constructors

	/** default constructor */
	public TbSubjectsInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tsi_id", unique = true, nullable = false, length=19)
	public Long getTsiId() {
		return this.tsiId;
	}
	
	public void setTsiId(Long tsiId) {
		this.tsiId = tsiId;
	}
	
	@Column(name = "tsi_name",  nullable = false, length=10)
	public String getTsiName() {
		return this.tsiName;
	}
	
	public void setTsiName(String tsiName) {
		this.tsiName = tsiName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tsi__add_date",  nullable = false, length=19)
	public Timestamp getTsiAddDate() {
		return this.tsiAddDate;
	}
	
	public void setTsiAddDate(Timestamp tsiAddDate) {
		this.tsiAddDate = tsiAddDate;
	}
	
	@Column(name = "tsi__add_person",  nullable = false, length=8)
	public String getTsiAddPerson() {
		return this.tsiAddPerson;
	}
	
	public void setTsiAddPerson(String tsiAddPerson) {
		this.tsiAddPerson = tsiAddPerson;
	}
	
	@Column(name = "tsi__status",  nullable = false, length=10)
	public Integer getTsiStatus() {
		return this.tsiStatus;
	}
	
	public void setTsiStatus(Integer tsiStatus) {
		this.tsiStatus = tsiStatus;
	}
	
	
}