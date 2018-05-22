package com.mingsoft.mdiy.biz.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.impl.BasicBizImpl;
import com.mingsoft.mdiy.biz.ISearchBiz;
import com.mingsoft.mdiy.dao.ISearchDao;
import com.mingsoft.mdiy.entity.SearchEntity;
import com.mingsoft.util.PageUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * 
 * <p>
 * <b>MYCMS-MY内容管理系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇MY科技有限公司
 * </p>
 * 
 * @author 姓名：张敏
 * 
 * @version 300-001-001
 * 
 * <p>
 * 版权所有 MY科技
 * </p>
 *  
 * <p>
 * Comments:搜索业务层实现类，继承BasicBizImpl，实现ISearchBiz
 * </p>
 *  
 * <p>
 * Create Date:2014-9-11
 * </p>
 *
 * <p>
 * Modification history:暂无
 * </p>
 */
@Service("searchBiz")
public class SearchBizImpl extends BasicBizImpl implements ISearchBiz{

	/**
	 * 搜索持久化层
	 */
	@Autowired
	private ISearchDao searchDao;
	
	/**
	 * 获取searchDao
	 */
	@Override
	protected IBaseDao getDao() {
		return searchDao;
	}

	@Override
	public SearchEntity getById(int searchId) {
		// TODO Auto-generated method stub
		SearchEntity search = new SearchEntity();
		search.setAppId(BasicUtil.getAppId());
		search.setSearchId(searchId);
		Object obj = searchDao.getByEntity(search);
		return obj!=null?(SearchEntity)obj:null;
	}
	
}
