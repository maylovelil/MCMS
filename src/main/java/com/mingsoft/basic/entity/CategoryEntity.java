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

package com.mingsoft.basic.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mingsoft.basic.entity.BaseEntity;
import com.mingsoft.util.StringUtil;

/**
 * 类别实体
 * 
 * @author 荣繁奎
 * @version 版本号：100-000-000<br/>
 *          创建日期：2012-03-15<br/>
 *          历史修订：<br/>
 */
public class CategoryEntity extends BaseEntity {

	/**
	 * 分类所属应用编号
	 */
	private int categoryAppId;

	/**
	 * 父类别的编号
	 */
	private int categoryCategoryId;

	/**
	 * 创建人
	 */
	private int categoryCreateBy;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date categoryCreateDate;

	/**
	 * 类别发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp categoryDateTime;

	/**
	 * 删除状态
	 */
	private int categoryDel;

	/**
	 * 栏目描述
	 */
	private String categoryDescription;

	/**
	 * 字典对应编号
	 */
	private int categoryDictId;

	/**
	 * 类别的编号自增长id
	 */
	private int categoryId;

	/**
	 * 栏目层级数量，方便控制后台分类的层级数量 不参与表结构
	 */
	private int categoryLevel;

	/**
	 * 发布用户编号(发布者编号)
	 */
	private int categoryManagerId;
	/**
	 * 所属模块编号
	 */
	private int categoryModelId;
	/**
	 * 父类型编号
	 */
	private String categoryParentId;
	/**
	 * 缩略图
	 */
	private String categorySmallImg;
	/**
	 * 类别的排序
	 */
	private int categorySort;
	/**
	 * 类别的标题
	 */
	private String categoryTitle;
	/**
	 * 更新人
	 */
	private int categoryUpdateBy;
	/**
	 * 更新时间
	 */
	private java.util.Date categoryUpdateDate;

	/**
	 * 一对多集合
	 */
	private List<CategoryEntity> childrenCategoryList;

	public CategoryEntity() {
		super();
	}

	public CategoryEntity(int appId, int modelId) {
		this.categoryAppId = appId;
		this.categoryModelId = modelId;
	}

	public CategoryEntity(int categoryId, String categoryTitle) {
		this.categoryId = categoryId;
		this.categoryTitle = categoryTitle;
	}

	/**
	 * 获取当前分类所有的子分类编号
	 * 
	 * @return int数组
	 */
	public int[] getAllCategroyChildrenIds() {
		List<CategoryEntity> categoryList = new ArrayList<CategoryEntity>();
		categoryList = this.getChilden(categoryList, this);
		int[] categoryIds = new int[categoryList.size()];
		int i = 0;
		for (CategoryEntity category : categoryList) {
			categoryIds[i++] = category.getCategoryId();
		}
		return categoryIds;
	}

	/**
	 * 获取当前分类所有的子分类
	 * 
	 * @return int数组
	 */
	public List<CategoryEntity> getAllCategroyChildrenList() {
		List<CategoryEntity> categoryList = new ArrayList<CategoryEntity>();
		return this.getChilden(categoryList, this);
	}

	public int getCategoryAppId() {
		return categoryAppId;
	}

	public int getCategoryCategoryId() {
		return categoryCategoryId;
	}

	public int getCategoryCreateBy() {
		return categoryCreateBy;
	}

	public java.util.Date getCategoryCreateDate() {
		return categoryCreateDate;
	}

	public Timestamp getCategoryDateTime() {
		return categoryDateTime;
	}

	public int getCategoryDel() {
		return categoryDel;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public int getCategoryDictId() {
		return categoryDictId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public int getCategoryLevel() {
		return categoryLevel;
	}

	public int getCategoryManagerId() {
		return categoryManagerId;
	}

	public int getCategoryModelId() {
		return categoryModelId;
	}

	public String getCategoryParentId() {
		return categoryParentId;
	}

	public String getCategorySmallImg() {
		return categorySmallImg;
	}

	public int getCategorySort() {
		return categorySort;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public int getCategoryUpdateBy() {
		return categoryUpdateBy;
	}

	public java.util.Date getCategoryUpdateDate() {
		return categoryUpdateDate;
	}

	/**
	 * 递归出当前分类所有的子分类
	 * 
	 * @param categoryList
	 *            子分类集合
	 * @param category
	 *            要递归的分类，默认当前
	 * @return 子分类集合
	 */
	private List<CategoryEntity> getChilden(List<CategoryEntity> categoryList, CategoryEntity category) {
		categoryList.add(category);
		if (category.getChildrenCategoryList() != null) {
			categoryList.addAll(category.getChildrenCategoryList());
			for (CategoryEntity _category : category.getChildrenCategoryList()) {
				return getChilden(categoryList, _category);
			}
		}
		return categoryList;
	}

	public List<CategoryEntity> getChildrenCategoryList() {
		if (childrenCategoryList == null) {
			childrenCategoryList = new ArrayList<CategoryEntity>();
		}
		return childrenCategoryList;
	}

	public void setCategoryAppId(int categoryAppId) {
		this.categoryAppId = categoryAppId;
	}

	public void setCategoryCategoryId(int categoryCategoryId) {
		this.categoryCategoryId = categoryCategoryId;
	}

	public void setCategoryCreateBy(int categoryCreateBy) {
		this.categoryCreateBy = categoryCreateBy;
	}

	public void setCategoryCreateDate(java.util.Date categoryCreateDate) {
		this.categoryCreateDate = categoryCreateDate;
	}

	public void setCategoryDateTime(Timestamp categoryDateTime) {
		this.categoryDateTime = categoryDateTime;
	}

	public void setCategoryDel(int categoryDel) {
		this.categoryDel = categoryDel;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public void setCategoryDictId(int categoryDictId) {
		this.categoryDictId = categoryDictId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public void setCategoryManagerId(int categoryManagerId) {
		this.categoryManagerId = categoryManagerId;
	}

	public void setCategoryModelId(int categoryModelId) {
		this.categoryModelId = categoryModelId;
	}

	public void setCategoryParentId(String categoryParentId) {
		this.categoryParentId = categoryParentId;
	}

	public void setCategorySmallImg(String categorySmallImg) {
		this.categorySmallImg = categorySmallImg;
	}

	public void setCategorySort(int categorySort) {
		this.categorySort = categorySort;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public void setCategoryUpdateBy(int categoryUpdateBy) {
		this.categoryUpdateBy = categoryUpdateBy;
	}

	public void setCategoryUpdateDate(java.util.Date categoryUpdateDate) {
		this.categoryUpdateDate = categoryUpdateDate;
	}

	public void setChildrenCategoryList(List<CategoryEntity> childrenCategoryList) {
		this.childrenCategoryList = childrenCategoryList;
	}

}