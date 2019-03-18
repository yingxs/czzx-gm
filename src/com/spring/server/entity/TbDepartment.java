package com.spring.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 *   部门表 entity.
 */
@Entity
@Table(name = "tb_department")
public class TbDepartment {
	private Integer tdId;  //部门表主键Id
	private String tdName; //名称
	private String tdDesp; //介绍
	private Timestamp tdAddtime;//添加时间
	private Long tdAdduser;//添加人
	private Integer tdStatus;//状态 0:删除 1:正常
	private String tgLogo;//图像
	private Integer tgIfManager;//是否是校长信箱 1:是  0:否
	private Integer tgIfPama;//是否是家校留言   1:是  0:否
	private Integer tgIfInner;//是否是心理咨询  1:是  0:否
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "td_id")
	public Integer getTdId() {
		return tdId;
	}
	public void setTdId(Integer tdId) {
		this.tdId = tdId;
	}
	@Column(name = "td_name")
	public String getTdName() {
		return tdName;
	}
	public void setTdName(String tdName) {
		this.tdName = tdName;
	}
	@Column(name = "td_desp")
	public String getTdDesp() {
		return tdDesp;
	}
	public void setTdDesp(String tdDesp) {
		this.tdDesp = tdDesp;
	}
	@Column(name = "td_addtime")
	public Timestamp getTdAddtime() {
		return tdAddtime;
	}
	public void setTdAddtime(Timestamp tdAddtime) {
		this.tdAddtime = tdAddtime;
	}
	@Column(name = "td_adduser")
	public Long getTdAdduser() {
		return tdAdduser;
	}
	public void setTdAdduser(Long tdAdduser) {
		this.tdAdduser = tdAdduser;
	}
	@Column(name = "td_status")
	public Integer getTdStatus() {
		return tdStatus;
	}
	public void setTdStatus(Integer tdStatus) {
		this.tdStatus = tdStatus;
	}
	@Column(name = "tg_logo")
	public String getTgLogo() {
		return tgLogo;
	}
	public void setTgLogo(String tgLogo) {
		this.tgLogo = tgLogo;
	}
	@Column(name = "tg_if_manager")
	public Integer getTgIfManager() {
		return tgIfManager;
	}
	public void setTgIfManager(Integer tgIfManager) {
		this.tgIfManager = tgIfManager;
	}
	@Column(name = "tg_if_pama")
	public Integer getTgIfPama() {
		return tgIfPama;
	}
	public void setTgIfPama(Integer tgIfPama) {
		this.tgIfPama = tgIfPama;
	}
	@Column(name = "tg_if_inner")
	public Integer getTgIfInner() {
		return tgIfInner;
	}
	public void setTgIfInner(Integer tgIfInner) {
		this.tgIfInner = tgIfInner;
	}
	public TbDepartment(Integer tdId, String tdName, String tdDesp, Timestamp tdAddtime, Long tdAdduser,
			Integer tdStatus, String tgLogo, Integer tgIfManager, Integer tgIfPama, Integer tgIfInner) {
		super();
		this.tdId = tdId;
		this.tdName = tdName;
		this.tdDesp = tdDesp;
		this.tdAddtime = tdAddtime;
		this.tdAdduser = tdAdduser;
		this.tdStatus = tdStatus;
		this.tgLogo = tgLogo;
		this.tgIfManager = tgIfManager;
		this.tgIfPama = tgIfPama;
		this.tgIfInner = tgIfInner;
	}
	public TbDepartment() {
		super();
	}


}
