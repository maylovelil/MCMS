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

import org.junit.experimental.theories.DataPoint;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.util.PageUtil;

/**
 * 类别业务层接口，继承接口IBaseBiz
 * 
 * @author 刘继平
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
public interface ICategoryBiz extends IBaseBiz {

	/**
	 * 根据分类ID查询子分类总数</br>
	 * 
	 * @param category
	 *            分类实体
	 * @return 返回子分类总数
	 */
	public int count(CategoryEntity category);



	/**
	 * 删除类别</br>
	 * 有拓展表继承时使用</br>
	 * 
	 * @param categoryId
	 *            类别ID
	 */
	public void deleteCategory(int categoryId);


	/**
	 * 根据ID批量查询分类实体
	 * 
	 * @param listId
	 *            ID集合
	 * @return 返回分类实体集合
	 */
	public List<CategoryEntity> queryBatchCategoryById(List<Integer> listId);



	/**
	 * 根据应用编号与模块编号查询分类
	 * 
	 * @param appId
	 *            应用编号
	 * @param modelId
	 *            模块编号
	 * @return 返回分类集合
	 */
	public List<CategoryEntity> queryByAppIdOrModelId(Integer appId, Integer modelId);

	/**
	 * 根据字典查询机构
	 * @param category
	 * @return
	 */
	List queryByDictId(CategoryEntity category);
	

	/**
	 * 分页查询</br>
	 * 查询分类集合</br>
	 * 
	 * @param category
	 *            查询条件
	 * @param page
	 *            对象，主要封装分页的方法
	 * @param orderBy
	 *            排序字段
	 * @param order
	 *            排序方式true:asc false:desc
	 * @return 返回分类集合
	 */
	@Deprecated
	public List queryByPageList(CategoryEntity category, PageUtil page, String orderBy, boolean order);


	/**
	 * 查询当前分类下面的子分类
	 * 
	 * @param categoryId
	 *            当前分类编号
	 * @param appId
	 *            应用编号
	 * @return 返回子分类列表集合
	 */
	@Deprecated
	public List<CategoryEntity> queryChildrenCategory(int categoryId, int appId, int modelId);

	/**
	 * 已过期，使用当前分类的categorys获取当前子类
	 * 查询当前分类下面的子分类的id
	 * 
	 * @param categoryId
	 *            当前分类编号
	 * @return appId 应用编号
	 * @return 返回子分类id集合
	 */
	@Deprecated
	public int[] queryChildrenCategoryIds(int categoryId, int appId, int modelId);

	/**
	 * 根据分类ID查询子分类</br>
	 * 
	 * @param category
	 *            分类实体
	 * @return 返回分类集合
	 */
	public List<CategoryEntity> queryChilds(CategoryEntity category);


	/**
	 * 添加类别</br>
	 * 有拓展表继承时使用</br>
	 * 
	 * @param categoryEntity
	 *            类别实体
	 * @return 返回类别ID
	 */
	public int saveCategory(CategoryEntity categoryEntity);
	
	/**
	 * 类别更新</br>
	 * 有拓展表继承时使用</br>
	 * 
	 * @param categoryEntity
	 *            类别实体
	 */
	public void updateCategory(CategoryEntity categoryEntity);

}