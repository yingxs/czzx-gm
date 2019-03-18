package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Organization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "organization")
public class Organization implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String code;
	private String innerPhone;
	private String outerPhone;
	private String manager;
	private String assistantManager;
	private String pax;
	private String zipcode;
	private String address;
	private Integer parentId;
	private String remark;
	private Integer sortcode;
	private Integer status;
	private Timestamp addtime;
	private Long addUser;
	private String addUsername;
	private Timestamp modiyftime;
	private Long modifyUser;
	private String modifyUsername;

	// Constructors

	/** default constructor */
	public Organization() {
	}

	/** minimal constructor */
	public Organization(String name, Integer status, Long addUser) {
		this.name = name;
		this.status = status;
		this.addUser = addUser;
	}

	/** full constructor */
	public Organization(String name, String code, String innerPhone,
			String outerPhone, String manager, String assistantManager,
			String pax, String zipcode, String address, Integer parentId,
			String remark, Integer sortcode, Integer status, Timestamp addtime,
			Long addUser, String addUsername, Timestamp modiyftime,
			Long modifyUser, String modifyUsername) {
		this.name = name;
		this.code = code;
		this.innerPhone = innerPhone;
		this.outerPhone = outerPhone;
		this.manager = manager;
		this.assistantManager = assistantManager;
		this.pax = pax;
		this.zipcode = zipcode;
		this.address = address;
		this.parentId = parentId;
		this.remark = remark;
		this.sortcode = sortcode;
		this.status = status;
		this.addtime = addtime;
		this.addUser = addUser;
		this.addUsername = addUsername;
		this.modiyftime = modiyftime;
		this.modifyUser = modifyUser;
		this.modifyUsername = modifyUsername;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "innerPhone", length = 20)
	public String getInnerPhone() {
		return this.innerPhone;
	}

	public void setInnerPhone(String innerPhone) {
		this.innerPhone = innerPhone;
	}

	@Column(name = "outerPhone", length = 20)
	public String getOuterPhone() {
		return this.outerPhone;
	}

	public void setOuterPhone(String outerPhone) {
		this.outerPhone = outerPhone;
	}

	@Column(name = "manager", length = 50)
	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Column(name = "assistantManager", length = 50)
	public String getAssistantManager() {
		return this.assistantManager;
	}

	public void setAssistantManager(String assistantManager) {
		this.assistantManager = assistantManager;
	}

	@Column(name = "pax", length = 20)
	public String getPax() {
		return this.pax;
	}

	public void setPax(String pax) {
		this.pax = pax;
	}

	@Column(name = "zipcode", length = 20)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "parentId")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sortcode")
	public Integer getSortcode() {
		return this.sortcode;
	}

	public void setSortcode(Integer sortcode) {
		this.sortcode = sortcode;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "addUser", nullable = false)
	public Long getAddUser() {
		return this.addUser;
	}

	public void setAddUser(Long addUser) {
		this.addUser = addUser;
	}

	@Column(name = "addUsername", length = 50)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}

	@Column(name = "modiyftime", length = 19)
	public Timestamp getModiyftime() {
		return this.modiyftime;
	}

	public void setModiyftime(Timestamp modiyftime) {
		this.modiyftime = modiyftime;
	}

	@Column(name = "modifyUser")
	public Long getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Column(name = "modifyUsername", length = 50)
	public String getModifyUsername() {
		return this.modifyUsername;
	}

	public void setModifyUsername(String modifyUsername) {
		this.modifyUsername = modifyUsername;
	}

}