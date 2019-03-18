package com.spring.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_applicaton_info")
public class ApplicatonInfo {
	private Integer taiId;
	private String taiName;
	private Integer taiType;
	private String taiWebAddr;
	private String taiIcon;
	private Date taiAddDate;
	private Integer taiAddCount;
	private Integer taiStatus;
	private String taContent;

	public ApplicatonInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the taiId
	 */
	@Id
	@GeneratedValue
	@Column(name = "tai_id", unique = true, nullable = false)
	public Integer getTaiId() {
		return taiId;
	}

	/**
	 * @param taiId
	 *            the taiId to set
	 */
	public void setTaiId(Integer taiId) {
		this.taiId = taiId;
	}

	/**
	 * @return the taiName
	 */
	@Column(name = "tai_name", nullable = false, length = 20)
	public String getTaiName() {
		return taiName;
	}

	@Column(name = "taContent", nullable = false, length = 500)
	public String getTaContent() {
		return taContent;
	}

	public void setTaContent(String taContent) {
		this.taContent = taContent;
	}

	/**
	 * @param taiName
	 *            the taiName to set
	 */
	public void setTaiName(String taiName) {
		this.taiName = taiName;
	}

	/**
	 * @return the taiType
	 */
	@Column(name = "tai_type", nullable = false, length = 4)
	public Integer getTaiType() {
		return taiType;
	}

	/**
	 * @param taiType
	 *            the taiType to set
	 */
	public void setTaiType(Integer taiType) {
		this.taiType = taiType;
	}

	/**
	 * @return the taiWebAddr
	 */
	@Column(name = "tai_web_addr", nullable = false, length = 256)
	public String getTaiWebAddr() {
		return taiWebAddr;
	}

	/**
	 * @param taiWebAddr
	 *            the taiWebAddr to set
	 */
	public void setTaiWebAddr(String taiWebAddr) {
		this.taiWebAddr = taiWebAddr;
	}

	/**
	 * @return the taiIcon
	 */
	@Column(name = "tai_icon", nullable = false, length = 256)
	public String getTaiIcon() {
		return taiIcon;
	}

	/**
	 * @param taiIcon
	 *            the taiIcon to set
	 */
	public void setTaiIcon(String taiIcon) {
		this.taiIcon = taiIcon;
	}

	/**
	 * @return the taiAddDate
	 */
	@Column(name = "tai_add_date", nullable = false)
	public Date getTaiAddDate() {
		return taiAddDate;
	}

	/**
	 * @param taiAddDate
	 *            the taiAddDate to set
	 */
	public void setTaiAddDate(Date taiAddDate) {
		this.taiAddDate = taiAddDate;
	}

	/**
	 * @return the taiAddCount
	 */
	@Column(name = "tai_add_count", length = 4)
	public Integer getTaiAddCount() {
		return taiAddCount;
	}

	/**
	 * @param taiAddCount
	 *            the taiAddCount to set
	 */
	public void setTaiAddCount(Integer taiAddCount) {
		this.taiAddCount = taiAddCount;
	}

	/**
	 * @return the taiStatus
	 */
	@Column(name = "tai_status", nullable = false, length = 4)
	public Integer getTaiStatus() {
		return taiStatus;
	}

	/**
	 * @param taiStatus
	 *            the taiStatus to set
	 */
	public void setTaiStatus(Integer taiStatus) {
		this.taiStatus = taiStatus;
	}

}
