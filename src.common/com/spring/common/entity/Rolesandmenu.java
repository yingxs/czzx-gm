package com.spring.common.entity;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Rolesandmenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "rolesandmenu")
public class Rolesandmenu implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer rolesId;
	private Integer menuId;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;

	// Constructors

	/** default constructor */
	public Rolesandmenu() {
	}

	/** minimal constructor */
	public Rolesandmenu(Integer rolesId, Integer menuId, Timestamp addtime,
			Long addUserId) {
		this.rolesId = rolesId;
		this.menuId = menuId;
		this.addtime = addtime;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Rolesandmenu(Integer rolesId, Integer menuId, Timestamp addtime,
			Long addUserId, String addUsername) {
		this.rolesId = rolesId;
		this.menuId = menuId;
		this.addtime = addtime;
		this.addUserId = addUserId;
		this.addUsername = addUsername;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "rolesId", nullable = false)
	public Integer getRolesId() {
		return this.rolesId;
	}

	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}

	@Column(name = "menuId", nullable = false)
	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "addUserId", nullable = false)
	public Long getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "addUsername", length = 50)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}

}