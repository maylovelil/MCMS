/**
The MIT License (MIT) * Copyright (c) 2016 MY科技(mingsoft.net)

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
 */

package com.mingsoft.basic.biz;
import java.util.List;

import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.basic.entity.ColumnEntity;

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
 * @author 刘继平
 * 
 * @version 300-001-001
 * 
 * <p>
 * 版权所有 MY科技
 * </p>
 *  
 * <p>
 * Comments:栏目业务层接口，继承ICategoryBiz接口
 * </p>
 *  
 * <p>
 * Create Date:2014-7-14
 * </p>
 *
 * <p>
 * Modification history:
 * </p>
 */
public interface IColumnBiz extends ICategoryBiz {

	
	
	/**
	 * 根据站点ID查询该站点下的栏目集合
	 * @param columnWebsiteId 站点Id
	 * @return 栏目集合
	 */
	public List<ColumnEntity> queryColumnListByWebsiteId(int columnWebsiteId);
	
	
	
	/**
	 * 根据站点Id查询该站点下的栏目的父栏目Id为categoryCategoryId的子栏目集合
	 * @param categoryCategoryId 父栏目ID
	 * @param columnWebsiteId 站点Id
	 * @param modelId 模块编号
	 * @return 栏目集合
	 */
	public List<ColumnEntity> queryChild(int categoryCategoryId,int columnWebsiteId,Integer modelId,Integer size);

	/**
	 * 获取当前应用下面对应模块的所以栏目分类
	 * @param appId 站点信息
	 * @param modelId 模块信息
	 * @return 记录集合
	 */
	public List<ColumnEntity> queryAll(int appId,int modelId);
	
	
	/**
	 * 通过栏目ID查询该栏目同级栏目
	 * @param columnId 栏目ID
	 * @return 同级栏目集合
	 */
	public List<ColumnEntity> querySibling(int columnId,Integer size);
	
	/**
	 * 通过栏目ID查询顶级栏目的同级栏目
	 * @param columnId 栏目ID
	 * @return 顶级同级栏目集合
	 */
	public List<ColumnEntity> queryTopSiblingListByColumnId(int columnId,Integer size);
	
	/**
	 * 根据栏目Id查询栏目的子栏目集
	 * @param columnId 栏目ID
	 * @return 子栏目集合
	 */
	public List<ColumnEntity> queryChildListByColumnId(int columnId,Integer size);
	
	
	/**
	 * 根据栏目ID查询其子栏目ID集合
	 * @param columnId 栏目ID
	 * @param appId 应用ID
	 * @return 子栏目ID集合
	 */
	public int[] queryChildIdsByColumnId(int columnId,int appId);
	
	
	/**
	 * 通过栏目ID查询栏目对应节点路径上的父级栏目集合
	 * @param columnId 栏目ID
	 * @return 栏目及其父级栏目集合
	 */
	public List<ColumnEntity> queryParentColumnByColumnId(int columnId);
	
	/**
	 * 根据站点Id查询该站点下的栏目的父栏目Id为categoryCategoryId的子栏目集合数目统计
	 * @param categoryCategoryId 父栏目ID
	 * @param columnWebsiteId 站点ID
	 * @return 子栏目统计数目
	 */
	public int queryColumnChildListCountByWebsiteId(int categoryCategoryId,int columnWebsiteId);

}