package com.mingsoft.basic.constant.e;


import com.mingsoft.base.constant.e.BaseEnum;

/**
 * 全局枚举类
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public enum GlobalModelCodelEnum implements BaseEnum{
	
	CITY("10990000");
	
	
	/**
	 * 设置CookieConst的常量
	 * @param attr 常量
	 */
	GlobalModelCodelEnum(String code) {
		this.code = code;
	}
	
	private String code;
	

	/**
	 * 返回该CookieConst常量的字符串表示
	 * @return 字符串
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return code;
	}


	@Override
	public int toInt() {
		return 0;
	}

	
}
