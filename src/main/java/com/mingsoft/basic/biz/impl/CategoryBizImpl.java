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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Basic;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.dao.ICategoryDao;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.FileUtil;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.base.util.BaseUtil;
import net.mingsoft.basic.util.BasicUtil;

/**
 * 类别业务层实现类，继承IBaseBiz，实现ICategoryBiz接口
 * 
 * @author 刘继平
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
@Service("categoryBiz")
public class CategoryBizImpl extends BaseBizImpl implements ICategoryBiz {

	/**
	 * 注入类别持久化层
	 */
	private ICategoryDao categoryDao;

	@Override
	public int count(CategoryEntity category) {
		// TODO Auto-generated method stub
		return categoryDao.count(category);
	}

	/**
	 * 递归返回生成静态页面的路径
	 * @param categoryId
	 * @return
	 */
    private String getGenerateFilePath(int categoryId,String categoryIds){
    	CategoryEntity category = (CategoryEntity) categoryDao.getEntity(categoryId);
    	int parentId = category.getCategoryCategoryId();
    	if (parentId != 0) {
    		categoryIds = parentId+File.separator + categoryIds;
    		return getGenerateFilePath(parentId, categoryIds);
    	}else{	
    	    String path = IParserRegexConstant.HTML_SAVE_PATH+File.separator+BasicUtil.getAppId()+File.separator+categoryIds;
    	    return path;
    	}
    }
	@Override
	public void deleteCategory(int categoryId) {
		// TODO Auto-generated method stub
		CategoryEntity category = (CategoryEntity) categoryDao.getEntity(categoryId);
		//删除父类
		if(category != null){
			//删除生成的html文件（递归方法获得文件路径）
			FileUtil.delFolders(BaseUtil.getRealPath(getGenerateFilePath(categoryId, categoryId+"")));
			categoryDao.deleteEntity(categoryId);
			deleteEntity(categoryId);
			category.setCategoryParentId(null);
			List<CategoryEntity> childrenList = categoryDao.queryChildren(category);
			for(int i = 0; i < childrenList.size(); i++){
				//删除子类
				int childrenId = childrenList.get(i).getCategoryId();
				deleteEntity(childrenId);
			}
		}
	}
	/**
	 * 获取类别持久化层
	 * 
	 * @return categoryDao 返回类别持久化层
	 */
	public ICategoryDao getCategoryDao() {
		return categoryDao;
	}

	/**
	 * 获取类别持久化层
	 * 
	 * @return categoryDao 返回类别持久话层
	 */
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return categoryDao;
	}


	@Override
	public BaseEntity getEntity(BaseEntity entity) {
		// TODO Auto-generated method stub
		CategoryEntity category = (CategoryEntity) super.getEntity(entity);
		if(category == null) {
			return null;
		}
		// 查询所有的子分类
		List<CategoryEntity> childs = categoryDao.query(category);
		resetChildren(category, childs);
		return category;

	}


	@Override
	public BaseEntity getEntity(int id) {
		// TODO Auto-generated method stub
		CategoryEntity category = (CategoryEntity) super.getEntity(id);
		if (category != null) {
			// 查询所有的子分类
			List<CategoryEntity> childs = categoryDao.queryChildren(category);
			resetChildren(category, childs);
		}
		return category;
	}


	@Override
	public List query(BaseEntity entity) {
		List list = super.query(entity);
		List childList = new ArrayList();
		childList.addAll(list);
		for (int i=0;i<list.size();i++) {
			CategoryEntity c = (CategoryEntity)list.get(i); 
			resetChildren(c, childList);
		}
		return list;
	}

	@Override
	public List<CategoryEntity> queryBatchCategoryById(List<Integer> listId) {
		// TODO Auto-generated method stub
		return categoryDao.queryBatchCategoryById(listId);
	}

	@Override
	public List<CategoryEntity> queryByAppIdOrModelId(Integer appId, Integer modelId) {
		// TODO Auto-generated method stub
		return categoryDao.queryByAppIdOrModelId(appId, modelId);
	}

	
	
	/**
	 * 根据字典查询机构
	 * @param category
	 * @return
	 */
	public List queryByDictId(CategoryEntity category) {
		return categoryDao.queryByDictId(category);
	}



	@Override
	public List queryByPageList(CategoryEntity category, PageUtil page, String orderBy, boolean order) {
		// TODO Auto-generated method stub
		return categoryDao.queryByPageList(category, page, orderBy, order);
	}

	@Override
	public List<CategoryEntity> queryChildrenCategory(int categoryId, int appId, int modelId) {
		// TODO Auto-generated method stub
		CategoryEntity category = new CategoryEntity();
		category.setCategoryAppId(appId);
		category.setCategoryModelId(modelId);
		category.setCategoryId(categoryId);
		
		return categoryDao.queryChildren(category);
	}

	@Override
	public synchronized int[] queryChildrenCategoryIds(int categoryId, int appId, int modelId) {
		// TODO Auto-generated method stub
		CategoryEntity category = new CategoryEntity();
		category.setCategoryAppId(appId);
		category.setCategoryModelId(modelId);
		category.setCategoryId(categoryId);
		List<CategoryEntity> list = categoryDao.queryChildren(category);
		int[] ids = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			CategoryEntity _category = list.get(i);
			ids[i] = _category.getCategoryId();
		}
		return ids;
	}

	@Override
	public List<CategoryEntity> queryChilds(CategoryEntity category) {
		// TODO Auto-generated method stub
		return categoryDao.queryChilds(category);
	}
	
	private void resetChildren(CategoryEntity category, List<CategoryEntity> chrildrenCategoryList) {
		for (CategoryEntity c : chrildrenCategoryList) {
			if (c.getCategoryCategoryId() == category.getCategoryId() && !category.getChildrenCategoryList().contains(c)) {
				category.getChildrenCategoryList().add(c);
				resetChildren(c, chrildrenCategoryList);
			}
		}
	}
	@Override
	public int saveCategory(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		if(categoryEntity.getCategoryCategoryId()>0) {
			CategoryEntity category = (CategoryEntity)this.getEntity(categoryEntity.getCategoryCategoryId());
			if(StringUtil.isBlank(category.getCategoryParentId())) {
				categoryEntity.setCategoryParentId(categoryEntity.getCategoryCategoryId()+"");
			} else {
				categoryEntity.setCategoryParentId(category.getCategoryParentId()+","+categoryEntity.getCategoryCategoryId());
			}
		}
		categoryDao.saveEntity(categoryEntity);
		return saveEntity(categoryEntity);
	}
	
	@Autowired
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	

	@Override
	public void updateCategory(CategoryEntity categoryEntity) {
		// TODO Auto-generated method stub
		if(categoryEntity.getCategoryCategoryId()>0) {
			CategoryEntity category = (CategoryEntity)this.getEntity(categoryEntity.getCategoryCategoryId());
			if(StringUtil.isBlank(category.getCategoryParentId())) {
				categoryEntity.setCategoryParentId(categoryEntity.getCategoryCategoryId()+"");
			} else {
				categoryEntity.setCategoryParentId(category.getCategoryParentId()+","+categoryEntity.getCategoryCategoryId());
			}
		}
		categoryDao.updateEntity(categoryEntity);
		updateEntity(categoryEntity);
	}
}