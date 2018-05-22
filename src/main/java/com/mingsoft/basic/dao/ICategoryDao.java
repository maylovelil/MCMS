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

package com.mingsoft.basic.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.util.PageUtil;

/**
 * 类别数据持久层，继承IBaseDao
 * @author 刘继平
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public interface ICategoryDao extends IBaseDao {

	/**
     * 分页查询</br>
     * 查询分类集合</br>
	 * @param category 分类实体
	 * @param page pageUtil实体
	 * @param orderBy 排序字段
	 * @param order 排序方式true:asc false:desc
	 * @return 返回分类集合
	 * @deprecated   推荐query
	 */
	public List<CategoryEntity> queryByPageList(@Param("category")CategoryEntity category,@Param("page")PageUtil page,@Param("orderBy")String orderBy,@Param("order") boolean order);
	
    /**
     * 根据分类ID查询子分类</br>
     * @param category 分类实体
     */
	@Deprecated
    public List<CategoryEntity> queryChilds(@Param("category")CategoryEntity category);
    
    
    /**
     * 根据分类ID查询子分类总数</br>
     * @param category 分类实体
     */
	@Deprecated
    public int count(@Param("category")CategoryEntity category);
    
    
    /**
     * 根据ID批量查询分类实体
     * @param listId ID集合
     * @return 返回分类实体
     */
    @Deprecated
    public List<CategoryEntity> queryBatchCategoryById(@Param("listId")List<Integer> listId);

	/**
	 * 根据应用编号与模块编号查询分类
	 * 
	 * @param appId 应用编号
	 * @param modelId 模块编号
	 * @return 返回分类集合
	 */
    @Deprecated
	public List<CategoryEntity> queryByAppIdOrModelId(@Param("appId")Integer appId,@Param("modelId") Integer modelId);

    
	
	/**
	 * 查询当前分类下面的所有子分类
	 * @param category 必须存在categoryId categoryParentId
	 * @return
	 */
	public List<CategoryEntity> queryChildren(CategoryEntity category);
	
	/**
	 * 根据字典查询机构
	 * @param category
	 * @return
	 */
	public List queryByDictId(CategoryEntity category);
}