package com.mingsoft.people.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mingsoft.people.entity.PeopleUserEntity;

/**
 * 
 * 会员扩展数据，用于后台数据查询
 * @author MY开发团队
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
public class PeopleBean extends PeopleUserEntity{

	private String  peopleDateTimes;
	
	private String startTime;
	
	private String endTime;

	public String getPeopleDateTimes() {
		return peopleDateTimes;
	}

	public void setPeopleDateTimes(String peopleDateTimes) {
		this.peopleDateTimes = peopleDateTimes;
	}

	public String getStartTime() {
		if(peopleDateTimes != null && peopleDateTimes != "" ){
			return peopleDateTimes.split("至")[0];
		}
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		if(peopleDateTimes != null && peopleDateTimes != "" ){
			return peopleDateTimes.split("至")[1];
		}
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
