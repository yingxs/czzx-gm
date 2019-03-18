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
 * 系信息表控制器
 *
 * @author hxx
 * @Date 2019-03-05 16:46:54
 */
@Entity
@Table(name = "tb_institute_department_info")
public class TbInstituteDepartmentInfo implements java.io.Serializable {
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//序列号
	private Long tidiId;
	//系名称
	private String tidiName;
	//所属学院


	private Long tidiInstituteId;
	//添加时间
	private Timestamp tidiAddDate;
	//添加人
	private Long tidiAddPerson;
	//状态1:正常  0:删除
	

	private Integer tidiStatus;
	//系编码
	private String tidiCode;
	//显示顺序
	private Integer tidiOrder;
	//图标
	private String tidiIcon;
	
	// Constructors

	/** default constructor */
	public TbInstituteDepartmentInfo() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tidi_id", unique = true, nullable = false, length=19)
	public Long getTidiId() {
		return this.tidiId;
	}
	
	public void setTidiId(Long tidiId) {
		this.tidiId = tidiId;
	}
	
	@Column(name = "tidi_name",  nullable = false, length=20)
	public String getTidiName() {
		return this.tidiName;
	}
	
	public void setTidiName(String tidiName) {
		this.tidiName = tidiName;
	}
	
	@Column(name = "tidi_institute_id",  nullable = false, length=19)
	public Long getTidiInstituteId() {
		return this.tidiInstituteId;
	}
	
	public void setTidiInstituteId(Long tidiInstituteId) {
		this.tidiInstituteId = tidiInstituteId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tidi_add_date",  nullable = false, length=19)
	public Timestamp getTidiAddDate() {
		return this.tidiAddDate;
	}
	
	public void setTidiAddDate(Timestamp tidiAddDate) {
		this.tidiAddDate = tidiAddDate;
	}
	
	@Column(name = "tidi_add_person",  nullable = false, length=19)
	public Long getTidiAddPerson() {
		return this.tidiAddPerson;
	}
	
	public void setTidiAddPerson(Long tidiAddPerson) {
		this.tidiAddPerson = tidiAddPerson;
	}
	
	@Column(name = "tidi_status",  nullable = false, length=10)
	public Integer getTidiStatus() {
		return this.tidiStatus;
	}
	
	public void setTidiStatus(Integer tidiStatus) {
		this.tidiStatus = tidiStatus;
	}
	
	@Column(name = "tidi_code",  nullable = false, length=10)
	public String getTidiCode() {
		return this.tidiCode;
	}
	
	public void setTidiCode(String tidiCode) {
		this.tidiCode = tidiCode;
	}
	
	@Column(name = "tidi_order",  nullable = false, length=10)
	public Integer getTidiOrder() {
		return this.tidiOrder;
	}
	
	public void setTidiOrder(Integer tidiOrder) {
		this.tidiOrder = tidiOrder;
	}
	
	@Column(name = "tidi_icon",  nullable = false, length=256)
	public String getTidiIcon() {
		return this.tidiIcon;
	}
	
	public void setTidiIcon(String tidiIcon) {
		this.tidiIcon = tidiIcon;
	}

	@Override
	public String toString() {
		return "TbInstituteDepartmentInfo [tidiId=" + tidiId + ", tidiName=" + tidiName + ", tidiInstituteId="
				+ tidiInstituteId + ", tidiAddDate=" + tidiAddDate + ", tidiAddPerson=" + tidiAddPerson
				+ ", tidiStatus=" + tidiStatus + ", tidiCode=" + tidiCode + ", tidiOrder=" + tidiOrder + ", tidiIcon="
				+ tidiIcon + "]";
	}
	
	
}