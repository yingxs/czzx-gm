package com.spring.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *   留言表 entity.
 */
@Entity
@Table(name = "tb_leave_message")
public class TbLeaveMessage {
	private Long tlmId;  //留言表主键Id
	private Long tlmAskAnswerId;//0代表游客；replyid不为0的表示回答，关联‘教师表’（注意ask_type的内容）；replyid=0的表示提问，关联‘学生表’
	private Integer tlmAskType;//提问人类型，1:学生家长2：教师
	private String tlmContent;//留言内容/回复内容
	private String ltmName;//留言姓名
	private String tlmMail;//电子邮箱
	private String tlmPhone;//联系电话
	private Timestamp tlmAddDate;//留言时间
	private Integer tlmStatus;//状态    1:留言提交 2:已审核(即待回复）3：审核失败4：已回复0:删除
	private Long tlmReply;//回复消息的id
	private Integer tlmType;//1：校长信箱2：家校留言3：心理咨询
	private Integer tlmIfSecret;//是否单独回复，1：是0：否
	private String tlmNumber;//查询码：手机号+5位随机码
	private Long tlmCheckPerson;//审核人，userinfo
	private Timestamp tlmCheckTime;//审核时间
	private String tlmBackMemo;//失败原因
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tlm_id")
	public Long getTlmId() {
		return tlmId;
	}
	public void setTlmId(Long tlmId) {
		this.tlmId = tlmId;
	}
	@Column(name = "tlm_ask_answer_id")
	public Long getTlmAskAnswerId() {
		return tlmAskAnswerId;
	}
	public void setTlmAskAnswerId(Long tlmAskAnswerId) {
		this.tlmAskAnswerId = tlmAskAnswerId;
	}
	@Column(name = "tlm_ask_type")
	public Integer getTlmAskType() {
		return tlmAskType;
	}
	public void setTlmAskType(Integer tlmAskType) {
		this.tlmAskType = tlmAskType;
	}
	@Column(name = "tlm_content")
	public String getTlmContent() {
		return tlmContent;
	}
	public void setTlmContent(String tlmContent) {
		this.tlmContent = tlmContent;
	}
	@Column(name = "ltm_name")
	public String getLtmName() {
		return ltmName;
	}
	public void setLtmName(String ltmName) {
		this.ltmName = ltmName;
	}
	@Column(name = "tlm_mail")
	public String getTlmMail() {
		return tlmMail;
	}
	public void setTlmMail(String tlmMail) {
		this.tlmMail = tlmMail;
	}
	@Column(name = "tlm_phone")
	public String getTlmPhone() {
		return tlmPhone;
	}
	public void setTlmPhone(String tlmPhone) {
		this.tlmPhone = tlmPhone;
	}
	@Column(name = "tlm_add_date")
	public Timestamp getTlmAddDate() {
		return tlmAddDate;
	}
	public void setTlmAddDate(Timestamp tlmAddDate) {
		this.tlmAddDate = tlmAddDate;
	}
	@Column(name = "tlm_status")
	public Integer getTlmStatus() {
		return tlmStatus;
	}
	public void setTlmStatus(Integer tlmStatus) {
		this.tlmStatus = tlmStatus;
	}
	@Column(name = "tlm_reply")
	public Long getTlmReply() {
		return tlmReply;
	}
	public void setTlmReply(Long tlmReply) {
		this.tlmReply = tlmReply;
	}
	@Column(name = "tlm_type")
	public Integer getTlmType() {
		return tlmType;
	}
	public void setTlmType(Integer tlmType) {
		this.tlmType = tlmType;
	}
	@Column(name = "tlm_if_secret")
	public Integer getTlmIfSecret() {
		return tlmIfSecret;
	}
	public void setTlmIfSecret(Integer tlmIfSecret) {
		this.tlmIfSecret = tlmIfSecret;
	}
	@Column(name = "tlm_number")
	public String getTlmNumber() {
		return tlmNumber;
	}
	public void setTlmNumber(String tlmNumber) {
		this.tlmNumber = tlmNumber;
	}
	@Column(name = "tlm_check_person")
	public Long getTlmCheckPerson() {
		return tlmCheckPerson;
	}
	public void setTlmCheckPerson(Long tlmCheckPerson) {
		this.tlmCheckPerson = tlmCheckPerson;
	}
	@Column(name = "tlm_check_time")
	public Timestamp getTlmCheckTime() {
		return tlmCheckTime;
	}
	public void setTlmCheckTime(Timestamp tlmCheckTime) {
		this.tlmCheckTime = tlmCheckTime;
	}
	@Column(name = "tlm_back_memo")
	public String getTlmBackMemo() {
		return tlmBackMemo;
	}
	public void setTlmBackMemo(String tlmBackMemo) {
		this.tlmBackMemo = tlmBackMemo;
	}
	public TbLeaveMessage(Long tlmId, Long tlmAskAnswerId, Integer tlmAskType, String tlmContent, String ltmName,
			String tlmMail, String tlmPhone, Timestamp tlmAddDate, Integer tlmStatus, Long tlmReply, Integer tlmType,
			Integer tlmIfSecret, String tlmNumber, Long tlmCheckPerson, Timestamp tlmCheckTime, String tlmBackMemo) {
		super();
		this.tlmId = tlmId;
		this.tlmAskAnswerId = tlmAskAnswerId;
		this.tlmAskType = tlmAskType;
		this.tlmContent = tlmContent;
		this.ltmName = ltmName;
		this.tlmMail = tlmMail;
		this.tlmPhone = tlmPhone;
		this.tlmAddDate = tlmAddDate;
		this.tlmStatus = tlmStatus;
		this.tlmReply = tlmReply;
		this.tlmType = tlmType;
		this.tlmIfSecret = tlmIfSecret;
		this.tlmNumber = tlmNumber;
		this.tlmCheckPerson = tlmCheckPerson;
		this.tlmCheckTime = tlmCheckTime;
		this.tlmBackMemo = tlmBackMemo;
	}
	public TbLeaveMessage() {
		super();
	}
	
}
