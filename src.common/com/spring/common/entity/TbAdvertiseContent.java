package com.spring.common.entity;

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
* 项目名称：XinYaOM   
* 类名称：TbAdvertiseContent   
* 类描述：    广告板块管理实体类
* 创建人：lm 
* 创建时间：2015年3月12日 下午5:27:33   
* @version    
*
 */
@Entity
@Table(name="tb_advertise_content")
public class TbAdvertiseContent implements java.io.Serializable{
	private Long tacId;
	private Long tacCataId;	//板块名称,tb_advertise_catalog
	private String tacContent;
	private String tacPhoneImage;//手机端广告页
	private String tacWebImage;//Web版广告页
	private Timestamp tacAddDate;//创建时间
	private Long tacCreater;//创建人
	private Integer tacStatus;//状态,1：正常 0: 删除
	private String tacLink;//广告链接
	
	/** default constructor */
	public TbAdvertiseContent() {
	}

	/** full constructor */
	public TbAdvertiseContent(Long tacId, Long tacCataId, 
			String tacPhoneImage, String tacWebImage, Timestamp tacAddDate,
			Long tacCreater, Integer tacStatus, String tacLink) {
		this.tacId = tacId;
		this.tacCataId = tacCataId;
		this.tacPhoneImage = tacPhoneImage;
		this.tacWebImage = tacWebImage;
		this.tacAddDate = tacAddDate;
		this.tacCreater = tacCreater;
		this.tacStatus = tacStatus;
		this.tacLink = tacLink;
	}

	@Id
	@GeneratedValue
	@Column(name = "tac_id", unique = true, nullable = false, length = 8)
	public Long getTacId() {
		return tacId;
	}

	public void setTacId(Long tacId) {
		this.tacId = tacId;
	}

	@Column(name = "tac_cata_id", nullable = false)
	public Long getTacCataId() {
		return tacCataId;
	}

	public void setTacCataId(Long tacCataId) {
		this.tacCataId = tacCataId;
	}

	@Column(name = "tac_phone_image")
	public String getTacPhoneImage() {
		return tacPhoneImage;
	}

	public void setTacPhoneImage(String tacPhoneImage) {
		this.tacPhoneImage = tacPhoneImage;
	}

	@Column(name = "tac_web_image")
	public String getTacWebImage() {
		return tacWebImage;
	}

	public void setTacWebImage(String tacWebImage) {
		this.tacWebImage = tacWebImage;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "tac_add_date", nullable = false, length = 19)
	public Timestamp getTacAddDate() {
		return tacAddDate;
	}

	public void setTacAddDate(Timestamp tacAddDate) {
		this.tacAddDate = tacAddDate;
	}

	@Column(name = "tac_creater")
	public Long getTacCreater() {
		return tacCreater;
	}

	public void setTacCreater(Long tacCreater) {
		this.tacCreater = tacCreater;
	}

	@Column(name = "tac_status", nullable = false)
	public Integer getTacStatus() {
		return tacStatus;
	}

	public void setTacStatus(Integer tacStatus) {
		this.tacStatus = tacStatus;
	}

	@Column(name = "tac_link")
	public String getTacLink() {
		return tacLink;
	}

	public void setTacLink(String tacLink) {
		this.tacLink = tacLink;
	}

	@Column(name = "tac_content")
	public String getTacContent() {
		return tacContent;
	}

	public void setTacContent(String tacContent) {
		this.tacContent = tacContent;
	}

	
}
