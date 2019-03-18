package com.spring.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Grade entity.
 */
@Entity
@Table(name = "tb_grade")
public class TbGrade {
	private Integer id;
	private String name;
	private Integer status;

	/** default constructor */
	public TbGrade() {
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public TbGrade(Integer id, String name, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name = "tg_id", unique = true, nullable = false)
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
	@Column(name = "tg_name", length = 20)
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
	 * @return the status
	 */
	@Column(name = "tg_status", length = 4)
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

}
