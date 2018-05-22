package com.mingsoft.people.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mingsoft.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

 /**
 * 用户基础信息表实体
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
public class PeopleUserEntity extends PeopleEntity {

	private static final long serialVersionUID = 1503454222227L;
	
	/**
	 * 用户ID关联people表的（people_id）
	 */
	private Integer puPeopleId; 
	/**
	 * 用户真实名称
	 */
	private String puRealName; 
	/**
	 * 用户地址
	 */
	private String puAddress; 
	/**
	 * 用户头像图标地址
	 */
	private String puIcon; 
	/**
	 * 用户昵称
	 */
	private String puNickname; 
	/**
	 * 用户性别(0.未知、1.男、2.女)
	 */
	private Integer puSex;
	/**
	 * 用户出生年月日
	 */
	private Date puBirthday; 
	/**
	 * 身份证
	 */
	private String puCard; 
	/**
	 * 省
	 */
	private long puProvince; 
	/**
	 * 城市
	 */
	private long puCity; 
	/**
	 * 区
	 */
	private long puDistrict; 
	/**
	 * 街道
	 */
	private long puStreet; 
	
	
		
	/**
	 * 设置用户ID关联people表的（people_id）
	 */
	public void setPuPeopleId(Integer puPeopleId) {
		this.puPeopleId = puPeopleId;
	}

	/**
	 * 获取用户ID关联people表的（people_id）
	 */
	public Integer getPuPeopleId() {
		return this.puPeopleId;
	}
	
	/**
	 * 设置用户真实名称
	 */
	public void setPuRealName(String puRealName) {
		this.puRealName = puRealName;
	}

	/**
	 * 获取用户真实名称
	 */
	public String getPuRealName() {
		return this.puRealName;
	}
	
	/**
	 * 设置用户地址
	 */
	public void setPuAddress(String puAddress) {
		this.puAddress = puAddress;
	}

	/**
	 * 获取用户地址
	 */
	public String getPuAddress() {
		return this.puAddress;
	}
	
	/**
	 * 设置用户头像图标地址
	 */
	public void setPuIcon(String puIcon) {
		this.puIcon = puIcon;
	}

	/**
	 * 获取用户头像图标地址
	 */
	public String getPuIcon() {
		return this.puIcon;
	}
	
	/**
	 * 设置用户昵称
	 */
	public void setPuNickname(String puNickname) {
		this.puNickname = puNickname;
	}

	/**
	 * 获取用户昵称
	 */
	public String getPuNickname() {
		return this.puNickname;
	}
	
	/**
	 * 设置用户性别(0.未知、1.男、2.女)
	 */
	public void setPuSex(Integer puSex) {
		this.puSex = puSex;
	}

	/**
	 * 获取用户性别(0.未知、1.男、2.女)
	 */
	public Integer getPuSex() {
		return this.puSex;
	}
	
	/**
	 * 设置用户出生年月日
	 */
	public void setPuBirthday(Date puBirthday) {
		this.puBirthday = puBirthday;
	}

	/**
	 * 获取用户出生年月日
	 */
	public Date getPuBirthday() {
		return this.puBirthday;
	}
	
	/**
	 * 设置身份证
	 */
	public void setPuCard(String puCard) {
		this.puCard = puCard;
	}

	/**
	 * 获取身份证
	 */
	public String getPuCard() {
		return this.puCard;
	}
	
	/**
	 * 设置省
	 */
	public void setPuProvince(long puProvince) {
		this.puProvince = puProvince;
	}

	/**
	 * 获取省
	 */
	public long getPuProvince() {
		return this.puProvince;
	}
	
	/**
	 * 设置城市
	 */
	public void setPuCity(long puCity) {
		this.puCity = puCity;
	}

	/**
	 * 获取城市
	 */
	public long getPuCity() {
		return this.puCity;
	}
	
	/**
	 * 设置区
	 */
	public void setPuDistrict(long puDistrict) {
		this.puDistrict = puDistrict;
	}

	/**
	 * 获取区
	 */
	public long getPuDistrict() {
		return this.puDistrict;
	}
	
	/**
	 * 设置街道
	 */
	public void setPuStreet(long puStreet) {
		this.puStreet = puStreet;
	}

	/**
	 * 获取街道
	 */
	public long getPuStreet() {
		return this.puStreet;
	}
	
}