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
@Table(name = "tb_photo")
public class TbPhoto implements Serializable {

	private static final long serialVersionUID = 8161259532789431912L;

	private Long tpId;

	private Long tpColumn;

	private String tpName;

	private String tpUrl;

	private Timestamp tpAddtime;

	private Long tpAdduser;

	private Integer tpStatus;

	private Long tpAlbumId;
	
	private int tpCoverPhoto;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private TbPhotoAlbum tbPhotoAlbum; 
	
	@Transient
	public TbPhotoAlbum getTbPhotoAlbum() {
		return tbPhotoAlbum;
	}

	public void setTbPhotoAlbum(TbPhotoAlbum tbPhotoAlbum) {
		this.tbPhotoAlbum = tbPhotoAlbum;
	}

	@Id
	@GeneratedValue
	@Column(name = "tp_id", unique = true, nullable = false)
	public Long getTpId() {
		return tpId;
	}

	public void setTpId(Long tpId) {
		this.tpId = tpId;
	}

	@Column(name = "tp_column")
	public Long getTpColumn() {
		return tpColumn;
	}

	public void setTpColumn(Long tpColumn) {
		this.tpColumn = tpColumn;
	}

	@Column(name = "tp_name")
	public String getTpName() {
		return tpName;
	}

	public void setTpName(String tpName) {
		this.tpName = tpName;
	}

	@Column(name = "tp_url")
	public String getTpUrl() {
		return tpUrl;
	}

	public void setTpUrl(String tpUrl) {
		this.tpUrl = tpUrl;
	}

	@Column(name = "tp_addtime")
	public Timestamp getTpAddtime() {
		return tpAddtime;
	}

	public void setTpAddtime(Timestamp tpAddtime) {
		this.tpAddtime = tpAddtime;
	}

	@Column(name = "tp_adduser")
	public Long getTpAdduser() {
		return tpAdduser;
	}

	public void setTpAdduser(Long tpAdduser) {
		this.tpAdduser = tpAdduser;
	}

	@Column(name = "tp_status")
	public Integer getTpStatus() {
		return tpStatus;
	}

	public void setTpStatus(Integer tpStatus) {
		this.tpStatus = tpStatus;
	}

	@Column(name = "tp_album_id")
	public Long getTpAlbumId() {
		return tpAlbumId;
	}

	public void setTpAlbumId(Long tpAlbumId) {
		this.tpAlbumId = tpAlbumId;
	}
	
	@Column(name = "tp_coverPhoto")
	public int getTpCoverPhoto() {
		return tpCoverPhoto;
	}

	public void setTpCoverPhoto(int tpCoverPhoto) {
		this.tpCoverPhoto = tpCoverPhoto;
	}

	public TbPhoto(Long tpId, Long tpColumn, String tpName, String tpUrl, Timestamp tpAddtime, Long tpAdduser, Integer tpStatus, Long tpAlbumId) {
		super();
		this.tpId = tpId;
		this.tpColumn = tpColumn;
		this.tpName = tpName;
		this.tpUrl = tpUrl;
		this.tpAddtime = tpAddtime;
		this.tpAdduser = tpAdduser;
		this.tpStatus = tpStatus;
		this.tpAlbumId = tpAlbumId;
	}

	public TbPhoto() {
		super();
	}

	@Override
	public String toString() {
		return "TbPhoto [tpId=" + tpId + ", tpColumn=" + tpColumn + ", tpName=" + tpName + ", tpUrl=" + tpUrl + ", tpAddtime=" + tpAddtime + ", tpAdduser=" + tpAdduser + ", tpStatus=" + tpStatus + ", tpAlbumId=" + tpAlbumId + "]";
	}

}
