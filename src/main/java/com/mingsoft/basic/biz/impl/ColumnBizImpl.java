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

package com.mingsoft.basic.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchange.v1.util.ArrayUtils;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.impl.CategoryBizImpl;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.basic.biz.IColumnBiz;
import com.mingsoft.basic.dao.IColumnDao;
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
 *          <p>
 *          版权所有 MY科技
 *          </p>
 * 
 *          <p>
 *          Comments:栏目业务层实现类，继承CategoryBizImpl，实现接口IColumnBiz
 *          </p>
 * 
 *          <p>
 *          Create Date:2014-7-14
 *          </p>
 *
 *          <p>
 *          Modification history:
 *          </p>
 */
@Service("columnBiz")
public class ColumnBizImpl extends CategoryBizImpl implements IColumnBiz {

	/**
	 * 栏目持久化层注入
	 */
	private IColumnDao columnDao;

	/**
	 * 获取 columnDao
	 * 
	 * @return columnDao
	 */
	public IColumnDao getColumnDao() {
		return columnDao;
	}

	/**
	 * 设置 columnDao
	 * 
	 * @param columnDao
	 */
	@Autowired
	public void setColumnDao(IColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	@Override
	protected IBaseDao getDao() {
		return columnDao;
	}

	/**
	 * 根据站点ID查询该站点下的栏目集合
	 * 
	 * @param columnWebsiteId
	 *            站点Id
	 * @return 栏目集合
	 */
	public List<ColumnEntity> queryColumnListByWebsiteId(int columnWebsiteId) {
		return columnDao.queryColumnListByWebsiteId(columnWebsiteId);
	}

	public List<ColumnEntity> queryChild(int categoryCategoryId, int columnWebsiteId, Integer modelId, Integer size) {
		return columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(categoryCategoryId, columnWebsiteId, modelId,
				size);
	}

	public List<ColumnEntity> queryAll(int appId, int modelId) {
		return columnDao.queryByAppIdAndModelId(appId, modelId);
	}


	/**
	 * 通过栏目的站点ID查询该站点下的栏目的父栏目Id为categoryCategoryId子栏目
	 * 通过递归查询将父栏目ID为categoryCategoryId的子栏目集合和他对应在同一节点树的父级栏目的集合全部查询装入List中
	 * 
	 * @param categoryCategoryId
	 *            栏目ID
	 * @param list
	 *            栏目集合
	 * @param columnWebsiteId
	 *            站点ID
	 */
	private void queryExpansionColumnListByWebsiteId(int categoryCategoryId, List<ColumnEntity> list,
			int columnWebsiteId) {
		List<ColumnEntity> queryChildList = new ArrayList<ColumnEntity>();
		queryChildList = columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(categoryCategoryId, columnWebsiteId,
				null, null);
		for (int i = 0; i < queryChildList.size(); i++) {
			list.add(queryChildList.get(i));
		}

		if (categoryCategoryId != 0) {
			ColumnEntity columnEntity = (ColumnEntity) (columnDao.getEntity(categoryCategoryId));
			queryExpansionColumnListByWebsiteId(columnEntity.getCategoryCategoryId(), list, columnWebsiteId);
		}
	}

	/**
	 * 通过栏目ID查询该栏目同级栏目
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 同级栏目集合
	 */
	public List<ColumnEntity> querySibling(int columnId, Integer size) {
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		List<ColumnEntity> list = new ArrayList<ColumnEntity>();
		if (columnEntity != null) {

			list = columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(columnEntity.getCategoryCategoryId(),
					columnEntity.getAppId(), null, size);
		}
		return list;
	}

	/**
	 * 通过栏目ID查询顶级栏目的同级栏目
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 顶级同级栏目集合
	 */
	public List<ColumnEntity> queryTopSiblingListByColumnId(int columnId, Integer size) {
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		List<ColumnEntity> list = null;
		if (columnEntity != null) {
			list = querySibling(columnEntity.getCategoryCategoryId(), size);
		}
		return list;
	}

	/**
	 * 根据栏目Id查询栏目的子栏目集
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 子栏目集合
	 */
	public List<ColumnEntity> queryChildListByColumnId(int columnId, Integer size) {
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		List<ColumnEntity> list = null;
		if (columnEntity != null) {
			list = columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(columnEntity.getCategoryId(),
					columnEntity.getAppId(), null, size);
		}
		return list;
	}

	/**
	 * 根据栏目ID查询其子栏目ID集合
	 * 
	 * @param categoryId
	 *            栏目ID
	 * @return 子栏目ID集合
	 */
	public int[] queryChildIdsByColumnId(int categoryId, int appId) {
		List<Integer> ids = columnDao.queryColumnChildIdList(categoryId, appId);
		int[] ret = new int[ids.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = ids.get(i).intValue();
		return ret;
	}


	/**
	 * 用递归通过栏目ID查询栏目的父级栏目,将查询结果装入List集合中
	 * 
	 * @param columnId
	 *            栏目ID
	 * @param list
	 *            父级栏目集合
	 */
	private void queryColumnParent(ColumnEntity column, List<ColumnEntity> list) {
		if (column.getCategoryCategoryId() != 0) {
			ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(column.getCategoryCategoryId());
			list.add(columnEntity);
			queryColumnParent(columnEntity, list);
		}
	}

	/**
	 * 通过栏目ID查询栏目对应节点路径上的父级栏目集合
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 栏目及其父级栏目集合
	 */
	public List<ColumnEntity> queryParentColumnByColumnId(int columnId) {
		List<ColumnEntity> list = null;
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		if (columnEntity != null) {
			list = new ArrayList<ColumnEntity>();
			// 递归的查询所有父节点
			queryColumnParent(columnEntity, list);
		}
		return list;
	}

	/**
	 * 根据站点Id查询该站点下的栏目的父栏目Id为categoryCategoryId的子栏目集合数目统计
	 * 
	 * @param categoryCategoryId
	 *            父栏目ID
	 * @param columnWebsiteId
	 *            站点ID
	 * @return 子栏目统计数目
	 */
	public int queryColumnChildListCountByWebsiteId(int categoryCategoryId, int columnWebsiteId) {
		return columnDao.queryColumnChildListCountByWebsiteId(categoryCategoryId, columnWebsiteId);
	}

}