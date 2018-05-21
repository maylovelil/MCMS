/**
The MIT License (MIT) * Copyright (c) 2016 木叶草科技(mingsoft.net)

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

package com.mingsoft.cms.parser.impl;



import com.mingsoft.parser.IParser;
import com.mingsoft.util.StringUtil;
/**
 * 
 * @ClassName:  ArticleTypeIdParser   
 * @Description:TODO 文章所在的栏目名称(单标签)	
 * @author: 木叶草开发团队
 * @date:   2018年1月31日 下午2:58:41   
 *     
 * @Copyright: 2018 www.mingsoft.net Inc. All rights reserved.
 */
public class ArticleTypeIdParser extends IParser{
	
	/**
	 * 文章所属栏目标题标签
	 */
	private final static String ARTICLE_TYPEID ="\\{ms:field.typeid(.*)?/\\}";
	
	private  final static String TYPE = "type";
	
	private final static String TYPE_TOP = "top";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent原HTML代码
	 * @param newContent替换的内容
	 */
	public ArticleTypeIdParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		return super.replaceAll(ARTICLE_TYPEID);
	}
	
	/**
	 *  是否存在type=top的属性
	 * @return　true:存在 false:不存在
	 */
	public boolean  isTop() {
		String temp  = super.getProperty(ARTICLE_TYPEID).get(TYPE);
		if (StringUtil.isBlank(temp)) {
			return false;
		} else {
			return temp.equalsIgnoreCase(TYPE_TOP) ;
		}
	}
	

}