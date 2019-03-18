package com.spring.common.entity;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Userandmenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userandmenu")
public class Userandmenu implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Integer menuId;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;
	private Integer type;
	private Integer groupType;

	// Constructors

	/** default constructor */
	public Userandmenu() {
	}

	/** minimal constructor */
	public Userandmenu(Long userId, Integer menuId, Timestamp addtime,
			Long addUserId) {
		this.userId = userId;
		this.menuId = menuId;
		this.addtime = addtime;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Userandmenu(Long userId, Integer menuId, Timestamp addtime,
			Long addUserId, String addUsername) {
		this.userId = userId;
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

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "menu_id", nullable = false)
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

	@Column(name = "add_userid", nullable = false)
	public Long getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "add_username", length = 50)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	@Column(name = "group_type")
	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	
	
	
	
	
}