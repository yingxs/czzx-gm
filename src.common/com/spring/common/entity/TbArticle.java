package com.spring.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TbArticle entity.
 */
@Entity
@Table(name = "tb_article")
public class TbArticle {
	private Integer taId;
	private Integer taColumnId;
	private String taTitle;
	private String taPicture;
	private String taContent;
	private Date taTopDate;
	private Integer taTop;
	private Integer taHeadline;
//	private Date taAddtime;
	private String taAddtime;
	private String taAdduser;
	private Integer taStatus;
	private String taUrl;
	private Integer taCount;
	
	/**界面转化字段**/
	private String taTopStr;
	private String taHeadlineStr;
	private String addtimeString;
	private String addUserName;
	private String columnName;
	
	public TbArticle(Integer taId, Integer taColumnId, String taTitle, String taPicture, String taContent, Date taTopDate, Integer taTop, Integer taHeadline, String taAddtime, String taAdduser, Integer taStatus, String taUrl,Integer taCount) {
		super();
		this.taId = taId;
		this.taColumnId = taColumnId;
		this.taTitle = taTitle;
		this.taPicture = taPicture;
		this.taContent = taContent;
		this.taTopDate = taTopDate;
		this.taTop = taTop;
		this.taHeadline = taHeadline;
		this.taAddtime = taAddtime;
		this.taAdduser = taAdduser;
		this.taStatus = taStatus;
		this.taUrl = taUrl;
		this.taCount = taCount;
	}
	
	public TbArticle() {
	}
	/**
	 * @return the taId
	 */
	@Id
	@GeneratedValue
	@Column(name = "ta_id", unique = true, nullable = false)
	public Integer getTaId() {
		return taId;
	}
	/**
	 * @param taId the taId to set
	 */
	public void setTaId(Integer taId) {
		this.taId = taId;
	}
	/**
	 * @return the taColumnId
	 */
	@Column(name = "ta_column_id", length = 8)
	public Integer getTaColumnId() {
		return taColumnId;
	}
	/**
	 * @param taColumnId the taColumnId to set
	 */
	public void setTaColumnId(Integer taColumnId) {
		this.taColumnId = taColumnId;
	}
	/**
	 * @return the taTitle
	 */
	@Column(name = "ta_title", length = 60)
	public String getTaTitle() {
		return taTitle;
	}
	/**
	 * @param taTitle the taTitle to set
	 */
	public void setTaTitle(String taTitle) {
		this.taTitle = taTitle;
	}
	/**
	 * @return the taPicture
	 */
	@Column(name = "ta_picture", nullable = false, length = 225)
	public String getTaPicture() {
		return taPicture;
	}
	/**
	 * @param taPicture the taPicture to set
	 */
	public void setTaPicture(String taPicture) {
		this.taPicture = taPicture;
	}
	/**
	 * @return the taContent
	 */
	@Column(name = "ta_content",  length = 2)
	public String getTaContent() {
		return taContent;
	}
	/**
	 * @param taContent the taContent to set
	 */
	public void setTaContent(String taContent) {
		this.taContent = taContent;
	}
	/**
	 * @return the taTopDate
	 */
	@Column(name = "ta_top_date")
	public Date getTaTopDate() {
		return taTopDate;
	}
	/**
	 * @param taTopDate the taTopDate to set
	 */
	public void setTaTopDate(Date taTopDate) {
		this.taTopDate = taTopDate;
	}
	/**
	 * @return the taTop
	 */
	@Column(name = "ta_top",  length = 4)
	public Integer getTaTop() {
		return taTop;
	}
	/**
	 * @param taTop the taTop to set
	 */
	public void setTaTop(Integer taTop) {
		this.taTop = taTop;
	}
	/**
	 * @return the taHeadline
	 */
	@Column(name = "ta_headline",  length = 4)
	public Integer getTaHeadline() {
		return taHeadline;
	}
	/**
	 * @param taHeadline the taHeadline to set
	 */
	public void setTaHeadline(Integer taHeadline) {
		this.taHeadline = taHeadline;
	}
	/**
	 * @return the taAddtime
	 */
	@Column(name = "ta_addtime")
	public String getTaAddtime() {
		return taAddtime;
	}
	/**
	 * @param taAddtime the taAddtime to set
	 */
	public void setTaAddtime(String taAddtime) {
	//	setAddtimeString(taAddtime);
		this.taAddtime = taAddtime;
	}
	/**
	 * @return the taAdduser
	 */
	@Column(name = "ta_adduser")
	public String getTaAdduser() {
		return taAdduser;
	}
	/**
	 * @param taAdduser the taAdduser to set
	 */
	public void setTaAdduser(String taAdduser) {
		this.taAdduser = taAdduser;
	}
	/**
	 * @return the taStatus
	 */
	@Column(name = "ta_status", length = 4)
	public Integer getTaStatus() {
		return taStatus;
	}
	/**
	 * @param taStatus the taStatus to set
	 */
	public void setTaStatus(Integer taStatus) {
		this.taStatus = taStatus;
	}
	/**
	 * @return the taUrl
	 */
	@Column(name = "ta_url", length = 225)
	public String getTaUrl() {
		return taUrl;
	}
	/**
	 * @param taUrl the taUrl to set
	 */
	public void setTaUrl(String taUrl) {
		this.taUrl = taUrl;
	}

	/**
	 * @return the taTopStr
	 */
	@Transient
	public String getTaTopStr() {
		if(this.getTaTop()==1){
			taTopStr = "是";
		}else if(this.getTaTop()==0){
			taTopStr = "否";
		}
		return taTopStr;
	}

	/**
	 * @param taTopStr the taTopStr to set
	 */
	public void setTaTopStr(String taTopStr) {
		this.taTopStr = taTopStr;
	}

	/**
	 * @return the taHeadlineStr
	 */
	@Transient
	public String getTaHeadlineStr() {
		if(this.getTaHeadline()==1){
			taHeadlineStr = "是";
		}else if(this.getTaHeadline()==0){
			taHeadlineStr = "否";
		}
		return taHeadlineStr;
	}

	/**
	 * @param taHeadlineStr the taHeadlineStr to set
	 */
	public void setTaHeadlineStr(String taHeadlineStr) {
		this.taHeadlineStr = taHeadlineStr;
	}
	@Transient
	public String getAddtimeString() {
		return addtimeString;
	}

	public void setAddtimeString(Date addtimeString) {
		if (addtimeString != null) {
			this.addtimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(addtimeString);
		}
	}

	/**
	 * @return the addUserName
	 */
	@Transient
	public String getAddUserName() {
		return addUserName;
	}

	/**
	 * @param addUserName the addUserName to set
	 */
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	/**
	 * @param addtimeString the addtimeString to set
	 */
	public void setAddtimeString(String addtimeString) {
		this.addtimeString = addtimeString;
	}

	/**
	 * @return the columnName
	 */
	@Transient
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	@Column(name = "ta_count", nullable = false, length = 4)
	public Integer getTaCount() {
		return taCount;
	}

	public void setTaCount(Integer taCount) {
		this.taCount = taCount;
	}
	
	
}
