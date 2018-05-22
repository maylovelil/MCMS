/**
The MIT License (MIT) * Copyright (c) 2016 MY科技

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */package com.mingsoft.people.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.entity.SessionEntity;

/**
 * 
 * 用户基础表
 * @author MY开发团队
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
public class PeopleEntity extends SessionEntity {

	/**
	 * 用户所属的应用ID
	 */
	private int peopleAppId;

	/**
	 * 不参表结构 大于0表示自动登录
	 */
	private int peopleAutoLogin;

	/**
	 * 用户随机验证码
	 */
	private String peopleCode;

	/**
	 * 发送验证码的时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp peopleCodeSendDate;

	/**
	 * 注册时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date peopleDateTime;
	
	

	/**
	 * 自增长ID
	 */
	private int peopleId;

	/**
	 * 用户邮箱</br>
	 * 可用作登录</br>
	 */
	private String peopleMail;

	/**
	 * 是否通过邮箱验证
	 */
	private int peopleMailCheck = -1;

	/**
	 * 登录帐号
	 */
	private String peopleName;
	
	
	/**
	 * 旧密码
	 */
	private String peopleOldPassword;

	/**
	 * 登录密码
	 */
	private String peoplePassword;

	/**
	 * 用户电话</br>
	 * 可用作登录</br>
	 */
	private String peoplePhone;

	/**
	 * 是否通过手机验证
	 */
	private int peoplePhoneCheck = -1;
	
	/**
	 * 用户状态 1.已审核 0.未审核
	 */
	private Integer peopleState;

	/**
	 * 用户真信息
	 */
	private PeopleUserEntity peopleUser;

	/**
	 * 获取peopleAppId
	 * 
	 * @return peopleAppId
	 */
	public int getPeopleAppId() {
		return peopleAppId;
	}

	public int getPeopleAutoLogin() {
		return peopleAutoLogin;
	}

	/**
	 * 获取用户随机码
	 * 
	 * @return
	 */
	public String getPeopleCode() {
		return peopleCode;
	}
	
	public Timestamp getPeopleCodeSendDate() {
		return peopleCodeSendDate;
	}

	/**
	 * 获取peopleDateTime
	 * 
	 * @return peopleDateTime
	 */
	public Date getPeopleDateTime() {
		return peopleDateTime;
	}

	/**
	 * 获取peopleId
	 * 
	 * @return peopleId
	 */
	public int getPeopleId() {
		return peopleId;
	}

	/**
	 * 获取peopleMail
	 * 
	 * @return peopleMail
	 */
	public String getPeopleMail() {
		return peopleMail;
	}

	public int getPeopleMailCheck() {
		return peopleMailCheck;
	}

	/**
	 * 获取peopleName
	 * 
	 * @return peopleName
	 */
	public String getPeopleName() {
		return peopleName;
	}

	public String getPeopleOldPassword() {
		return peopleOldPassword;
	}

	/**
	 * 获取peoplePassword
	 * 
	 * @return peoplePassword
	 */
	public String getPeoplePassword() {
		return peoplePassword;
	}

	/**
	 * 获取peoplePhone
	 * 
	 * @return peoplePhone
	 */
	public String getPeoplePhone() {
		return peoplePhone;
	}

	public int getPeoplePhoneCheck() {
		return peoplePhoneCheck;
	}

	/**
	 * 获取peopleState
	 * 
	 * @return peopleState
	 */
	public Integer getPeopleState() {
		return peopleState;
	}

	public PeopleUserEntity getPeopleUser() {
		return peopleUser;
	}

	/**
	 * 设置peopleAppId
	 * 
	 * @param peopleAppId
	 */
	public void setPeopleAppId(int peopleAppId) {
		this.peopleAppId = peopleAppId;
	}

	public void setPeopleAutoLogin(int peopleAutoLogin) {
		this.peopleAutoLogin = peopleAutoLogin;
	}

	/**
	 * 设置用户随机码
	 * 
	 * @param peopleCode
	 */
	public void setPeopleCode(String peopleCode) {
		this.peopleCode = peopleCode;
	}

	public void setPeopleCodeSendDate(Timestamp peopleCodeSendDate) {
		this.peopleCodeSendDate = peopleCodeSendDate;
	}

	/**
	 * 设置peopleDateTime
	 * 
	 * @param peopleDateTime
	 */
	public void setPeopleDateTime(Date peopleDateTime) {
		this.peopleDateTime = peopleDateTime;
	}

	/**
	 * 设置peopleId
	 * 
	 * @param peopleId
	 */
	public void setPeopleId(int peopleId) {
		this.peopleId = peopleId;
	}

	/**
	 * 设置peopleMail
	 * 
	 * @param peopleMail
	 */
	public void setPeopleMail(String peopleMail) {
		this.peopleMail = peopleMail;
	}

	public void setPeopleMailCheck(BaseEnum check) {
		this.peopleMailCheck = check.toInt();
	}

	/**
	 * 推荐使用枚举类形参方法
	 * 
	 * @param peopleMailCheck
	 */
	@Deprecated
	public void setPeopleMailCheck(int peopleMailCheck) {
		this.peopleMailCheck = peopleMailCheck;
	}

	/**
	 * 设置peopleName
	 * 
	 * @param peopleName
	 */
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public void setPeopleOldPassword(String peopleOldPassword) {
		this.peopleOldPassword = peopleOldPassword;
	}

	/**
	 * 设置peoplePwd
	 * 
	 * @param peoplePassword
	 */
	public void setPeoplePassword(String peoplePassword) {
		this.peoplePassword = peoplePassword;
	}

	/**
	 * 设置peoplePhone
	 * 
	 * @param peoplePhone
	 */
	public void setPeoplePhone(String peoplePhone) {
		this.peoplePhone = peoplePhone;
	}

	public void setPeoplePhoneCheck(BaseEnum check) {
		this.peoplePhoneCheck = check.toInt();
	}

	/**
	 * 推荐使用枚举类形参方法
	 * 
	 * @param peoplePhoneCheck
	 */
	@Deprecated
	public void setPeoplePhoneCheck(int peoplePhoneCheck) {
		this.peoplePhoneCheck = peoplePhoneCheck;
	}

	/**
	 * 设置peopleState
	 * 
	 * @param peopleState
	 */
	@JsonIgnore
	public void setPeopleStateEnum(BaseEnum e) {
		this.peopleState = e.toInt();
	}

	/**
	 * 设置peopleState，控制层推荐使用setPeopleState(BaseEnum e) 方法
	 * 
	 * @see setPeopleState(BaseEnum e)
	 * @param peopleState
	 */
	public void setPeopleState(Integer peopleState) {
		this.peopleState = peopleState;
	}

	public void setPeopleUser(PeopleUserEntity peopleUser) {
		this.peopleUser = peopleUser;
	}

	
	
}
