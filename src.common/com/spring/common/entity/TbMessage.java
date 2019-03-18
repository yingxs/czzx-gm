package com.spring.common.entity;

// default package
// Generated 2014-10-24 15:59:42 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * 
 * 项目名称：XinYaOM 类名称：TbMessage 类描述： 消息实体类 创建人：lm 创建时间：2015年3月12日 下午5:31:27
 * 
 * @version
 * 
 */
@Entity
@Table(name = "tb_message")
public class TbMessage implements java.io.Serializable {

	private Long tmId;
	private Long tmMemberId;//会员id/经销商id/水工id
	private String tmTitle;// 消息名称
	private String tmContent;// 消息内容
	private Timestamp tmAddtime;// 创建日期
	private Long tmAddPerson;// 添加人
	private Integer tmStatus;// 状态  0：删除 1：正常（未读） 2：已读
	private String tmNumber;//编号
	private Integer tmType;//1：客户（会员）  2：经销商  3：水工

	public TbMessage() {
	}

	public TbMessage(Long tmId, String tmTitle, String tmContent,
			Long tmMemberId, Timestamp tmAddtime, Long tmAddPerson,
			Integer tmStatus, String tmNumber, Integer tmType) {
		this.tmId = tmId;
		this.tmTitle = tmTitle;
		this.tmContent = tmContent;
		this.tmMemberId = tmMemberId;
		this.tmAddtime = tmAddtime;
		this.tmAddPerson = tmAddPerson;
		this.tmStatus = tmStatus;
		this.tmNumber = tmNumber;
		this.tmType = tmType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tm_id", unique = true, nullable = false)
	public Long getTmId() {
		return tmId;
	}

	public void setTmId(Long tmId) {
		this.tmId = tmId;
	}

	@Column(name = "tm_title", nullable = false)
	public String getTmTitle() {
		return tmTitle;
	}

	public void setTmTitle(String tmTitle) {
		this.tmTitle = tmTitle;
	}

	@Column(name = "tm_content", nullable = false)
	public String getTmContent() {
		return tmContent;
	}

	public void setTmContent(String tmContent) {
		this.tmContent = tmContent;
	}

	@Column(name = "tm_member_id", nullable = false)
	public Long getTmMemberId() {
		return tmMemberId;
	}

	public void setTmMemberId(Long tmMemberId) {
		this.tmMemberId = tmMemberId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tm_addtime", nullable = false, length = 19)
	public Timestamp getTmAddtime() {
		return tmAddtime;
	}

	public void setTmAddtime(Timestamp tmAddtime) {
		this.tmAddtime = tmAddtime;
	}

	@Column(name = "tm_addperson", nullable = false)
	public Long getTmAddPerson() {
		return tmAddPerson;
	}

	public void setTmAddPerson(Long tmAddPerson) {
		this.tmAddPerson = tmAddPerson;
	}

	@Column(name = "tm_status", nullable = false)
	public Integer getTmStatus() {
		return tmStatus;
	}

	public void setTmStatus(Integer tmStatus) {
		this.tmStatus = tmStatus;
	}

	@Column(name = "tm_type")
	public Integer getTmType() {
		return tmType;
	}

	public void setTmType(Integer tmType) {
		this.tmType = tmType;
	}

	@Column(name = "tm_number")
	public String getTmNumber() {
		return tmNumber;
	}

	public void setTmNumber(String tmNumber) {
		this.tmNumber = tmNumber;
	}

}
