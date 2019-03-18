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
 * 年级表控制器
 *
 * @author zhouyuan
 * @Date 2019-03-15 14:35:02
 */
@Entity
@Table(name = "tb_grade")
public class TbGrade implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tgId;
	//年级名称
	private String tgName;
	//状态1:正常 0:删除
	private Integer tgStatus;
	
	// Constructors

	/** default constructor */
	public TbGrade() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tg_id", unique = true, nullable = false, length=19)
	public Long getTgId() {
		return this.tgId;
	}
	
	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}
	
	@Column(name = "tg_name",  nullable = false, length=20)
	public String getTgName() {
		return this.tgName;
	}
	
	public void setTgName(String tgName) {
		this.tgName = tgName;
	}
	
	@Column(name = "tg_status",  nullable = false, length=10)
	public Integer getTgStatus() {
		return this.tgStatus;
	}
	
	public void setTgStatus(Integer tgStatus) {
		this.tgStatus = tgStatus;
	}
	
	
}