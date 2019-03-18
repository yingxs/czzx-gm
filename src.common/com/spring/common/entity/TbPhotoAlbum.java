package com.spring.common.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_photo_album")
public class TbPhotoAlbum implements Serializable {

	private static final long serialVersionUID = -4914032013069269054L;

	private Long tpaId;

	private Integer tpaType;

	private String tpaName;

	private Timestamp tpaAddime;

	private Long tpaAdduser;

	private Integer tpaStatus;
	
	@Id
	@GeneratedValue
	@Column(name = "tpa_id", unique = true, nullable = false)
	public Long getTpaId() {
		return tpaId;
	}

	public void setTpaId(Long tpaId) {
		this.tpaId = tpaId;
	}

	@Column(name = "tpa_name")
	public String getTpaName() {
		return tpaName;
	}

	public void setTpaName(String tpaName) {
		this.tpaName = tpaName;
	}

	@Column(name = "tpa_addime")
	public Timestamp getTpaAddime() {
		return tpaAddime;
	}

	public void setTpaAddime(Timestamp tpaAddime) {
		this.tpaAddime = tpaAddime;
	}

	@Column(name = "tpa_adduser")
	public Long getTpaAdduser() {
		return tpaAdduser;
	}

	public void setTpaAdduser(Long tpaAdduser) {
		this.tpaAdduser = tpaAdduser;
	}

	@Column(name = "tpa_status")
	public Integer getTpaStatus() {
		return tpaStatus;
	}

	public void setTpaStatus(Integer tpaStatus) {
		this.tpaStatus = tpaStatus;
	}

	@Column(name = "tpa_type", unique = true, nullable = false)
	public Integer getTpaType() {
		return tpaType;
	}

	public void setTpaType(Integer tpaType) {
		this.tpaType = tpaType;
	}
	
	public TbPhotoAlbum() {
		super();
	}

	public TbPhotoAlbum(Long tpaId, Long tpaColumnId, String tpaName, Timestamp tpaAddime, Long tpaAdduser, Integer tpaStatus,Integer tpaType) {
		super();
		this.tpaId = tpaId;
		this.tpaName = tpaName;
		this.tpaAddime = tpaAddime;
		this.tpaAdduser = tpaAdduser;
		this.tpaStatus = tpaStatus;
		this.tpaType = tpaType;
	}

	@Override
	public String toString() {
		return "TbPhotoAlbum [tpaId=" + tpaId + ", tpaType=" + tpaType + ", tpaName=" + tpaName + ", tpaAddime=" + tpaAddime + ", tpaAdduser=" + tpaAdduser + ", tpaStatus=" + tpaStatus + "]";
	}


}
