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

package com.mingsoft.basic.action.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.e.GlobalModelCodelEnum;
import com.mingsoft.basic.entity.CategoryEntity;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 供前端查询分类信息使用
 * @author 史爱华
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
@Controller("webCategory")
@RequestMapping("/category")
public class CategoryAction extends BaseAction{
	
	/**
	 * 分类业务处理层注入
	 */
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 根据分类id查找其父分类实体,如果父分类不存在则返回该分类实体
	 * @param categoryId 分类ID
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 */
	@RequestMapping("/{categoryId}/getParentCategory")
	@ResponseBody
	public void getParentCategory(@PathVariable int categoryId,HttpServletRequest request, HttpServletResponse response){
		CategoryEntity category = (CategoryEntity)categoryBiz.getEntity(categoryId);
		if(category!=null){
			CategoryEntity paCategory = (CategoryEntity)categoryBiz.getEntity(category.getCategoryCategoryId());
			if(paCategory==null){
				this.outJson(response, JSONObject.toJSONString(category));
			}
			this.outJson(response, JSONObject.toJSONString(paCategory));
		}
	}
	
	/**
	 * 根据指定分类id查询其子分类
	 * @param categoryId 分类id
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 */
	@RequestMapping("/{categoryId}/queryChildren")
	public void queryChildren(@PathVariable int categoryId,HttpServletRequest request, HttpServletResponse response){
		CategoryEntity category = (CategoryEntity) this.categoryBiz.getEntity(categoryId);
		if(category!=null){
			List<CategoryEntity> list = this.categoryBiz.queryChilds(category);
			this.outJson(response, JSONObject.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss"));
		}
	}
}