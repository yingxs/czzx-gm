package com.spring.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_student_family")
public class TbStudentFamily implements Serializable {

	private static final long serialVersionUID = 8761199776158131667L;

	private Long id;

	private Long tsfStudentId;

	private String tsfName;

	private String tsfPhone;

	private String tsfRelation;

	@Id
	@GeneratedValue
	@Column(name = "tsf_id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "tsf_student_id", nullable = false, length = 8)
	public Long getTsfStudentId() {
		return tsfStudentId;
	}

	public void setTsfStudentId(Long tsfStudentId) {
		this.tsfStudentId = tsfStudentId;
	}

	@Column(name = "tsf_name")
	public String getTsfName() {
		return tsfName;
	}

	public void setTsfName(String tsfName) {
		this.tsfName = tsfName;
	}

	@Column(name = "tsf_phone")
	public String getTsfPhone() {
		return tsfPhone;
	}

	public void setTsfPhone(String tsfPhone) {
		this.tsfPhone = tsfPhone;
	}

	@Column(name = "tsf_relation")
	public String getTsfRelation() {
		return tsfRelation;
	}

	public void setTsfRelation(String tsfRelation) {
		this.tsfRelation = tsfRelation;
	}

	public TbStudentFamily(Long id, Long tsfStudentId, String tsfName,
			String tsfPhone, String tsfRelation) {
		super();
		this.id = id;
		this.tsfStudentId = tsfStudentId;
		this.tsfName = tsfName;
		this.tsfPhone = tsfPhone;
		this.tsfRelation = tsfRelation;
	}

	public TbStudentFamily() {
		super();
	}

	@Override
	public String toString() {
		return "TbStudentFamily [id=" + id + ", tsfStudentId=" + tsfStudentId
				+ ", tsfName=" + tsfName + ", tsfPhone=" + tsfPhone
				+ ", tsfRelation=" + tsfRelation + "]";
	}

}
