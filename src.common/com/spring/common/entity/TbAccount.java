package com.spring.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_account")
public class TbAccount {

	private Long taId;
	private String taAccount;
	private String taPassword;
	private Integer taType;
	private Integer taStatus;
	
	@Id
	@GeneratedValue
	@Column(name = "ta_id", unique = true, nullable = false)
	public Long getTaId() {
		return taId;
	}
	public void setTaId(Long taId) {
		this.taId = taId;
	}
	@Column(name = "ta_account")
	public String getTaAccount() {
		return taAccount;
	}
	public void setTaAccount(String taAccount) {
		this.taAccount = taAccount;
	}
	@Column(name = "ta_password")
	public String getTaPassword() {
		return taPassword;
	}
	public void setTaPassword(String taPassword) {
		this.taPassword = taPassword;
	}
	@Column(name = "ta_type")
	public Integer getTaType() {
		return taType;
	}
	public void setTaType(Integer taType) {
		this.taType = taType;
	}
	@Column(name = "ta_status")
	public Integer getTaStatus() {
		return taStatus;
	}
	public void setTaStatus(Integer taStatus) {
		this.taStatus = taStatus;
	}
	
	public TbAccount(Long taId, String taAccount, String taPassword,
			Integer taType, Integer taStatus) {
		super();
		this.taId = taId;
		this.taAccount = taAccount;
		this.taPassword = taPassword;
		this.taType = taType;
		this.taStatus = taStatus;
	}
	
	public TbAccount() {
		super();
	}
	
}
