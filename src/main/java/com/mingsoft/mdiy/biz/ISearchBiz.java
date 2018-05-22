package com.mingsoft.mdiy.biz;
import java.util.List;
import java.util.Map;

import com.mingsoft.basic.biz.IBasicBiz;
import com.mingsoft.mdiy.entity.SearchEntity;
import com.mingsoft.util.PageUtil;

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
 * Comments:搜索业务层，继承IBasicBiz
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
public interface ISearchBiz extends IBasicBiz{
	
	/**
	 * 根据id查询对应站点的搜索
	 * @param searchId 搜索记录编号
	 * @return
	 */
	SearchEntity getById(int searchId);
}
