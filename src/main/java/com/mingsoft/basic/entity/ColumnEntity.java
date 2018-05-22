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

import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.util.StringUtil;

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
 * Comments: 栏目管理实体类,继承CategoryEntity
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
public class ColumnEntity extends CategoryEntity {
	
	/**
	 * 最终栏目列表
	 * 推荐使用：ColumnTypeEnum
	 */
	@Deprecated
	public final static int COLUMN_TYPE_LIST=1;
	
	/**
	 * 单页
	 * 推荐使用：ColumnTypeEnum
	 */
	@Deprecated
	public final static int COLUMN_TYPE_COVER=2;
	
	/**
	 * 连接地址
	 * 推荐使用：ColumnTypeEnum
	 */
	@Deprecated
	public final static int COLUMN_TYPE_URL = 3;	
	
	/**
	 * 栏目简介
	 */
	private String columnKeyword;
	
	/**
	 * 栏目关键字的扩展
	 */
	private String columnDescrip;
	
	/**
	 * 栏目属性
	 * @see ColumnTypeEnum
	 */
	private int columnType;
	
	
	
	/**
	 * 如果为最终栏目列表，则保持栏目列表的地址
	 * 如果为外部链接，则保存外部链接的地址
	 */
	private String columnUrl;
	
	/**
	 * 最终列表栏目的列表模板地址
	 */
	private String columnListUrl;
	
	/**
	 * 栏目类型，直接影响栏目发布的表单样式
	 */
	private int columnContentModelId;
	
	
	/**
	 * 栏目保存路径
	 */
	private String columnPath;
	

	/**
	 * 获取栏目简介的扩展
	 * @return columnDescrip
	 */
	public String getColumnDescrip() {
		return columnDescrip;
	}

	/**
	 * 获取栏目简介
	 * @return columnKeyword
	 */
	public String getColumnKeyword() {
		return columnKeyword;
	}

	/**
	 *  获取最终列表栏目的列表模板地址
	 * @return columnListUrl
	 */
	public String getColumnListUrl() {
		return columnListUrl;
	}

	public String getColumnPath() {
		if(StringUtil.isBlank(columnPath)){
			return columnPath;
		}
		return columnPath.replace("\\", "/");
	}

	/**
	 * 获取栏目下的文章所属表单的类型
	 * @return
	 */
	public int getColumnContentModelId() {
		return columnContentModelId;
	}

	/**
	 * 获取栏目属性对应的值
	 * 1，COLUMN_TYPE_LIST 代表最终栏目列表
	 * 2， COLUMN_TYPE_COVER代表频道封面
	 * @return columnType
	 */
	public int getColumnType() {
		return columnType;
	}

	/**
	 * 获取栏目对应连接
	 * @return
	 */
	public String getColumnUrl() {
		return columnUrl;
	}

	/**
	 * 设置栏目简介的扩展
	 * @param columnDescrip
	 */
	public void setColumnDescrip(String columnDescrip) {
		this.columnDescrip = columnDescrip;
	}

	/**
	 * 设置栏目简介
	 * @param columnKeyword
	 */
	public void setColumnKeyword(String columnKeyword) {
		this.columnKeyword = columnKeyword;
	}

	/**
	 * 设置最终列表栏目的列表模板地址
	 * @param columnListUrl
	 */
	public void setColumnListUrl(String columnListUrl) {
		this.columnListUrl = columnListUrl;
	}

	public void setColumnPath(String columnPath) {
		this.columnPath = columnPath;
	}

	/**
	 * 设置栏目下的文章所属表单的类型
	 * @return
	 */
	public void setColumnContentModelId(int columnContentModelId) {
		this.columnContentModelId = columnContentModelId;
	}

	/**
	 * 设置栏目属性对应的值
	 * 
	 * @param columnType
	 */
	@Deprecated
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	
	/**
	 * 设置栏目对应连接
	 * @param columnUrl
	 */
	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}

	
	
	public enum ColumnTypeEnum implements BaseEnum {
		/**
		 * 列表
		 */
		COLUMN_TYPE_LIST(1),
		/**
		 * 单页
		 */
		COLUMN_TYPE_COVER(2),
		/**
		 * 跳转地址
		 */
		COLUMN_TYPE_URL(3);

		ColumnTypeEnum(Object code) {
			this.code = code;
		}
		
		private Object code;
		@Override
		public int toInt() {
			// TODO Auto-generated method stub
			return Integer.valueOf(code+"");
		}
		
	}
	
}