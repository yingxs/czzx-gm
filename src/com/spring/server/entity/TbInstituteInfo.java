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
 * 学院信息表控制器
 *
 * @author
 * @Date 2019-03-05 14:07:28
 */
@Entity
@Table(name = "tb_institute_info")
public class TbInstituteInfo implements java.io.Serializable {
	
	// Fields
	
	//序列号
	private Long tiiId;
	//学院名称
	private String tiiName;
	//学院log
	private String tiiIcon;
	//所属大学
	private Long tiiSechoolId;
	//添加时间
	private Timestamp tiiAddDate;
	//添加人
	private Long tiiAddPerson;
	//状态 
	private Integer tiiStatus;
	//排序
	private Integer tiiOrder;
	//学院编码
	private String tiiCode;
	
	// Constructors

	/** default constructor */
	public TbInstituteInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tii_id", unique = true, nullable = false, length=19)
	public Long getTiiId() {
		return this.tiiId;
	}
	
	public void setTiiId(Long tiiId) {
		this.tiiId = tiiId;
	}
	
	@Column(name = "tii_name",  nullable = false, length=30)
	public String getTiiName() {
		return this.tiiName;
	}
	
	public void setTiiName(String tiiName) {
		this.tiiName = tiiName;
	}
	
	@Column(name = "tii_icon",  nullable = false, length=256)
	public String getTiiIcon() {
		return this.tiiIcon;
	}
	
	public void setTiiIcon(String tiiIcon) {
		this.tiiIcon = tiiIcon;
	}
	
	@Column(name = "tii_sechool_id",  nullable = false, length=19)
	public Long getTiiSechoolId() {
		return this.tiiSechoolId;
	}
	
	public void setTiiSechoolId(Long tiiSechoolId) {
		this.tiiSechoolId = tiiSechoolId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tii_add_date",  nullable = false, length=19)
	public Timestamp getTiiAddDate() {
		return this.tiiAddDate;
	}
	
	public void setTiiAddDate(Timestamp tiiAddDate) {
		this.tiiAddDate = tiiAddDate;
	}
	
	@Column(name = "tii_add_person",  nullable = false, length=19)
	public Long getTiiAddPerson() {
		return this.tiiAddPerson;
	}
	
	public void setTiiAddPerson(Long tiiAddPerson) {
		this.tiiAddPerson = tiiAddPerson;
	}
	
	@Column(name = "tii_status",  nullable = false, length=10)
	public Integer getTiiStatus() {
		return this.tiiStatus;
	}
	
	public void setTiiStatus(Integer tiiStatus) {
		this.tiiStatus = tiiStatus;
	}
	
	@Column(name = "tii_order",  nullable = false, length=10)
	public Integer getTiiOrder() {
		return this.tiiOrder;
	}
	
	public void setTiiOrder(Integer tiiOrder) {
		this.tiiOrder = tiiOrder;
	}
	
	@Column(name = "tii_code",  nullable = false, length=20)
	public String getTiiCode() {
		return this.tiiCode;
	}
	
	public void setTiiCode(String tiiCode) {
		this.tiiCode = tiiCode;
	}

	@Override
	public String toString() {
		return "TbInstituteInfo [tiiId=" + tiiId + ", tiiName=" + tiiName + ", tiiIcon=" + tiiIcon + ", tiiSechoolId="
				+ tiiSechoolId + ", tiiAddDate=" + tiiAddDate + ", tiiAddPerson=" + tiiAddPerson + ", tiiStatus="
				+ tiiStatus + ", tiiOrder=" + tiiOrder + ", tiiCode=" + tiiCode + "]";
	}
	
	
}