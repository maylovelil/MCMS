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
 */package com.mingsoft.people.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.PageUtil;

/**
 * 
 * 用户业务层，继承IBaseBiz
 * @author MY开发团队
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
public interface IPeopleBiz  extends IBaseBiz{

	/**
	 * 根据用户ID删除用户实体，用于有子类的删除操作
	 * @param id 用户ID
	 */
	public void deletePeople(int id);	
	
	
	/**
	 * 批量删除用户
	 * @param peopleIds 用户id集合
	 */
	public void deletePeople(int[] peopleIds);	
	
	/**
	 * 更具用户实体获取用户信息,
	 * @param people 用户实体，可以设置用户名、用户邮箱、用户手机号
	 * @param appId 应用id
	 * @return 用户实体
	 */
	PeopleEntity getByPeople(PeopleEntity people,int appId);	
	
	/**
	 * 根据应用id和其他查询条件查询用户总数
	 * @param appId 应用id
	 * @param whereMap 其他查询条件 key:字段属性名 value:字段属性值
	 * @return 用户总数
	 */
	int getCountByAppIdAndMap(int appId,Map whereMap);
	
	/**
	 * 根据注册时间和应用id查询总数
	 * @param peopleDateTime
	 * @param appId 应用id
	 * @return 用户总数
	 */
	public int getCountByDate(String peopleDateTime,Integer appId);
	
	/**
	 * 根据用户名(帐号,手机,邮箱)和验证码查询用户信息开始
	 * @param userName 用户名
	 * @param peopleCode 验证码
	 * @param appId 应用id
	 * @return 用户实体
	 */
	public PeopleEntity getEntityByCode(String userName,String peopleCode,int appId);
	
	/**
	 * 根据用户用户名查询用户实体</br>
	 * @param userName 用户名(注:手机号,邮箱)
	 * @param appId 应用Id
	 * @return 查询到的用户实体
	 */
	PeopleEntity getEntityByMailOrPhone(String userName,int appId);
	
	/**
	 * 根据用户用户名查询用户实体</br>
	 * @param userName 用户名(注:手机号,邮箱,用户名称都可作为用户名登录)
	 * @param appId 应用Id
	 * @return 查询到的用户实体
	 */
	public PeopleEntity getEntityByUserName(String userName,int appId);
	
	/**
	 * 根据应用id和其他查询条件查询用户列表信息
	 * @param appId 应用id
	 * @param whereMap 其他查询条件 key:字段属性名 value:字段属性值
	 * @return 用户列表信息
	 * 
	 */
	@Deprecated
	public List<PeopleEntity> queryByAppIdAndMap(int appId,Map whereMap,PageUtil page);
	
	/**
	 * 用户查询
	 * @param appId 应用编号
	 * @param people 用户
	 * @return
	 */
	public List<PeopleEntity> query(int appId,Map where);
	
	/**
	 * 根据应用ID查询用户总数
	 * @param appId 应用ID
	 * @return 用户总数
	 */
	@Deprecated
	public int queryCountByAppId(int appId);
	
	
	
	/**
	 * 根据AppId查询用户列表并进行分页
	 * @param appId 应用Id
	 * @param page 分页
	 * @return 用户集合
	 */
	@Deprecated
	public List<PeopleEntity> queryPageListByAppId(int appId,PageUtil page);
	
	/**
	 * 用户有子类添加
	 * @param entity 用户实体
	 * @return 用户ID
	 */
	public int savePeople(PeopleEntity people);
	
	/**
	 * 根据用户ID进行用户实体的更新，用于有子类的更新操作
	 * @param entity 用户实体
	 */
	public void updatePeople(PeopleEntity people);
	
}
