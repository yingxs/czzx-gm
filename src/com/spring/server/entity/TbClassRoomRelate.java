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
 * 班级教室关联表控制器
 *
 * @author wanghao
 * @Date 2019-03-08 11:46:40
 */
@Entity
@Table(name = "tb_class_room_relate")
public class TbClassRoomRelate implements java.io.Serializable {
	
	// Fields
	
	//序号
	private Long tcrrId;
	//班级id
	private Long tcrrClassId;
	//教室id
	private Long tcrrRoomId;
	//添加时间
	private Timestamp tcrrAddTime;
	//添加人
	private String tcrrAddPerson;
	
	// Constructors

	/** default constructor */
	public TbClassRoomRelate() {
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tcrr_id", unique = true, nullable = false, length=19)
	public Long getTcrrId() {
		return this.tcrrId;
	}
	
	public void setTcrrId(Long tcrrId) {
		this.tcrrId = tcrrId;
	}
	
	@Column(name = "tcrr_class_id",  nullable = false, length=19)
	public Long getTcrrClassId() {
		return this.tcrrClassId;
	}
	
	public void setTcrrClassId(Long tcrrClassId) {
		this.tcrrClassId = tcrrClassId;
	}
	
	@Column(name = "tcrr_room_id",  nullable = false, length=19)
	public Long getTcrrRoomId() {
		return this.tcrrRoomId;
	}
	
	public void setTcrrRoomId(Long tcrrRoomId) {
		this.tcrrRoomId = tcrrRoomId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "tcrr_add_time",  nullable = false, length=19)
	public Timestamp getTcrrAddTime() {
		return this.tcrrAddTime;
	}
	
	public void setTcrrAddTime(Timestamp tcrrAddTime) {
		this.tcrrAddTime = tcrrAddTime;
	}
	
	@Column(name = "tcrr_add_person",  nullable = false, length=10)
	public String getTcrrAddPerson() {
		return this.tcrrAddPerson;
	}
	
	public void setTcrrAddPerson(String tcrrAddPerson) {
		this.tcrrAddPerson = tcrrAddPerson;
	}
	
	
}