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
 */package com.mingsoft.people.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.biz.IPeopleUserBiz;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.people.biz.IPeopleStudentBiz;
import com.mingsoft.people.dao.IPeopleStudentDao;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.people.entity.PeopleStudentEntity;
import com.mingsoft.util.PageUtil;

/**
 * 
 * 用户信息业务层层实现类
 * @author MY开发团队
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
@Service("peopleStudentBiz")
public class PeopleStudentBizImpl extends PeopleBizImpl implements IPeopleStudentBiz {
	
	/**
	 * 学生信息持久化层注入
	 */
	@Autowired
	private IPeopleStudentDao peopleStudentDao;
	
	@Autowired
	private IPeopleUserBiz peopleUserBiz;
	
	@Autowired
	private IPeopleBiz peopleBiz;
	
	@Autowired
	private ICategoryBiz categoryBiz;

	@Override
	protected IBaseDao getDao() {
		return peopleStudentDao;
	}
	
	@Override
	public int savePeopleStudent(PeopleStudentEntity peopleStudentEntity) {
		peopleUserBiz.savePeople(peopleStudentEntity);
		return peopleStudentDao.saveEntity(peopleStudentEntity);
	}

	@Override
	public void updatePeopleStudent(PeopleStudentEntity peopleStudentEntity) {
		// TODO Auto-generated method stub
		peopleUserBiz.updateEntity(peopleStudentEntity);
		peopleBiz.updatePeople(peopleStudentEntity);
		this.peopleStudentDao.updateEntity(peopleStudentEntity);
	}

	@Override
	public void deletePeopleStudent(int peopleId) {
		deletePeople(peopleId);
		this.peopleStudentDao.deleteEntity(peopleId);
	}

	@Override
	public PeopleStudentEntity getPeopleStudent(int peopleId) {
		// TODO Auto-generated method stub
		return this.peopleStudentDao.getPeopleStudent(peopleId);
	}

	@Override
	public List<PeopleStudentEntity> queryListPageByAppId(Integer appId,
			PageUtil page) {
		// TODO Auto-generated method stub
		return peopleStudentDao.queryPageListByAppId(appId, page);
	}
	
	
	
	
	@Override
	public Map getStudentInfor(Integer peopleId) {
		
		PeopleStudentEntity peopleStudent = (PeopleStudentEntity) this.peopleStudentDao.getPeopleStudent(peopleId);
		//查询学生的城市信息
		CategoryEntity city = (CategoryEntity)categoryBiz.getEntity(peopleStudent.getPeopleStudentCityID());
		
		
		Map map = new HashMap();
		if(city!=null){
			map.put("city", city.getCategoryTitle());
			CategoryEntity province = (CategoryEntity)categoryBiz.getEntity(city.getCategoryCategoryId());
			if(province!=null){
				map.put("province", province.getCategoryTitle());
			}
		}
		
		map.put("peopleStudent", peopleStudent);
		return map;
	}
	
	
	@Override
	public List<PeopleStudentEntity> queryByMap(Integer appId,PageUtil page ,Map where){
		
		return this.peopleStudentDao.queryByMap(appId, page, where);
	}

	@Override
	public int getCountByMap(Integer appId, Map where) {
		// TODO Auto-generated method stub
		return this.peopleStudentDao.getCountByMap(appId, where);
	}
	
	
}
