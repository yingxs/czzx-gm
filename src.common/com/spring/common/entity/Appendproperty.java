package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Appendproperty entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "appendproperty")
public class Appendproperty implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String function;
	private String functonUrl;
	private String controlId;
	private String controlDatasource;
	private Integer controlLength;
	private Integer controlMaxLen;
	private String controlStyle;
	private String controlValidator;
	private Integer colspan;
	private String event;
	private Integer sortCode;
	private Integer status;
	private Long addUserId;
	private String addUsername;
	private Timestamp addtime;
	private Timestamp modifytime;
	private Long modifyUserId;
	private String modifyUsername;

	// Constructors

	/** default constructor */
	public Appendproperty() {
	}

	/** minimal constructor */
	public Appendproperty(Integer sortCode, Long addUserId) {
		this.sortCode = sortCode;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Appendproperty(String name, String function, String functonUrl,
			String controlId, String controlDatasource, Integer controlLength,
			Integer controlMaxLen, String controlStyle,
			String controlValidator, Integer colspan, String event,
			Integer sortCode, Integer status, Long addUserId,
			String addUsername, Timestamp addtime, Timestamp modifytime,
			Long modifyUserId, String modifyUsername) {
		this.name = name;
		this.function = function;
		this.functonUrl = functonUrl;
		this.controlId = controlId;
		this.controlDatasource = controlDatasource;
		this.controlLength = controlLength;
		this.controlMaxLen = controlMaxLen;
		this.controlStyle = controlStyle;
		this.controlValidator = controlValidator;
		this.colspan = colspan;
		this.event = event;
		this.sortCode = sortCode;
		this.status = status;
		this.addUserId = addUserId;
		this.addUsername = addUsername;
		this.addtime = addtime;
		this.modifytime = modifytime;
		this.modifyUserId = modifyUserId;
		this.modifyUsername = modifyUsername;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "function", length = 50)
	public String getFunction() {
		return this.function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	@Column(name = "functonUrl", length = 200)
	public String getFunctonUrl() {
		return this.functonUrl;
	}

	public void setFunctonUrl(String functonUrl) {
		this.functonUrl = functonUrl;
	}

	@Column(name = "controlId", length = 50)
	public String getControlId() {
		return this.controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	@Column(name = "controlDatasource", length = 250)
	public String getControlDatasource() {
		return this.controlDatasource;
	}

	public void setControlDatasource(String controlDatasource) {
		this.controlDatasource = controlDatasource;
	}

	@Column(name = "controlLength")
	public Integer getControlLength() {
		return this.controlLength;
	}

	public void setControlLength(Integer controlLength) {
		this.controlLength = controlLength;
	}

	@Column(name = "controlMaxLen")
	public Integer getControlMaxLen() {
		return this.controlMaxLen;
	}

	public void setControlMaxLen(Integer controlMaxLen) {
		this.controlMaxLen = controlMaxLen;
	}

	@Column(name = "controlStyle", length = 50)
	public String getControlStyle() {
		return this.controlStyle;
	}

	public void setControlStyle(String controlStyle) {
		this.controlStyle = controlStyle;
	}

	@Column(name = "controlValidator", length = 50)
	public String getControlValidator() {
		return this.controlValidator;
	}

	public void setControlValidator(String controlValidator) {
		this.controlValidator = controlValidator;
	}

	@Column(name = "colspan")
	public Integer getColspan() {
		return this.colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	@Column(name = "event", length = 50)
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Column(name = "sortCode", nullable = false)
	public Integer getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Column(name = "addtime", length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "modifytime", length = 19)
	public Timestamp getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "modifyUserId")
	public Long getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "modifyUsername", length = 50)
	public String getModifyUsername() {
		return this.modifyUsername;
	}

	public void setModifyUsername(String modifyUsername) {
		this.modifyUsername = modifyUsername;
	}

}