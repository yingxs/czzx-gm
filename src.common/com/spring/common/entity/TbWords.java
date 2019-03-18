package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbWords entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_words")
public class TbWords implements java.io.Serializable {

	// Fields

	private Long twId;
	private String twCode;
	private String twName;
	private Timestamp twAddDate;
	private Long twAddPerson;
	private Integer twStatus;
	private String twValue;
	// 备注
	private String twMemo;

	// Constructors

	/** default constructor */
	public TbWords() {
	}

	/** minimal constructor */
	public TbWords(String twCode, String twName, Integer twStatus) {
		this.twCode = twCode;
		this.twName = twName;
		this.twStatus = twStatus;
	}

	/** full constructor */
	public TbWords(String twCode, String twName, Timestamp twAddDate, Long twAddPerson, Integer twStatus,
			String twValue) {
		this.twCode = twCode;
		this.twName = twName;
		this.twAddDate = twAddDate;
		this.twAddPerson = twAddPerson;
		this.twStatus = twStatus;
		this.twValue = twValue;
	}

	@Column(name = "tw_memo", nullable = false, length = 10)
	public String getTwMemo() {
		return twMemo;
	}

	public void setTwMemo(String twMemo) {
		this.twMemo = twMemo;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tw_id", unique = true, nullable = false)
	public Long getTwId() {
		return this.twId;
	}

	public void setTwId(Long twId) {
		this.twId = twId;
	}

	@Column(name = "tw_code", nullable = false, length = 10)
	public String getTwCode() {
		return this.twCode;
	}

	public void setTwCode(String twCode) {
		this.twCode = twCode;
	}

	@Column(name = "tw_name", nullable = false, length = 50)
	public String getTwName() {
		return this.twName;
	}

	public void setTwName(String twName) {
		this.twName = twName;
	}

	@Column(name = "tw_add_date", length = 19)
	public Timestamp getTwAddDate() {
		return this.twAddDate;
	}

	public void setTwAddDate(Timestamp twAddDate) {
		this.twAddDate = twAddDate;
	}

	@Column(name = "tw_add_person")
	public Long getTwAddPerson() {
		return this.twAddPerson;
	}

	public void setTwAddPerson(Long twAddPerson) {
		this.twAddPerson = twAddPerson;
	}

	@Column(name = "tw_status", nullable = false)
	public Integer getTwStatus() {
		return this.twStatus;
	}

	public void setTwStatus(Integer twStatus) {
		this.twStatus = twStatus;
	}

	@Column(name = "tw_value", length = 20)
	public String getTwValue() {
		return this.twValue;
	}

	public void setTwValue(String twValue) {
		this.twValue = twValue;
	}

}