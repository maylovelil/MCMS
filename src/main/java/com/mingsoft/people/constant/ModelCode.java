/**
The MIT License (MIT) * Copyright (c) 2016 MY科技

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
 */package com.mingsoft.people.constant;

import com.mingsoft.base.constant.e.BaseEnum;

/**
 * 
 * MY会员系统 会员模块编码
 * @author MY开发团队
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-23 10:10:22<br/>
 * 历史修订：<br/>
 */
public enum ModelCode implements BaseEnum{

	/**
	 * 用户信息模块
	 */
	PEOPLE("07000000"),
	
	/**
	 * 用户注册
	 */
	PEOPLE_REGISTER("07010100"),
	
	/**
	 * 用户登录
	 */
	PEOPLE_LOGIN("07010200"),
	
	/**
	 * 普通用户管理
	 */
	PEOPLE_USER("07020100");
	

	
	/**
	 * 设置modelCode的常量
	 * @param code 常量
	 */
	ModelCode(String code) {
		this.code = code;
	}

	private String code;

	/**
	 * 返回该modelCode常量的字符串表示
	 * @return 字符串
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * 返回该modelCode常量的整型表示
	 * @return 整型
	 */
	public int toInt() {
		// TODO Auto-generated method stub
		return Integer.parseInt(code);
	}
}
