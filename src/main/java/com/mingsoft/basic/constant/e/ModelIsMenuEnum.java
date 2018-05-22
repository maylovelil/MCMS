package com.mingsoft.basic.constant.e;

import com.mingsoft.base.constant.e.BaseEnum;

/**
 * 是否是菜单枚举类
 * @Package com.mingsoft.basic.constant.e 
 * @author 李书宇
 * @version 
 * 版本号：<br/>
 * 创建日期：@date 2015年10月21日<br/>
 * 历史修订：<br/>
 */
public enum ModelIsMenuEnum implements BaseEnum{
	/**
	 * 是否是菜单1非菜单
	 */
	MODEL_NOTMENU(1,"否"),
	
	/**
	 * 是否是菜单0菜单
	 */
	MODEL_MEUN(0,"是");
	
	private String code;
	
	private int id;

	/**
	 * 构造方法
	 * @param id 默认ID
	 * @param code 传入的枚举类型
	 */
	ModelIsMenuEnum(int id,String code) {
		this.code = code;
		this.id = id;
	}

	@Override
	public int toInt() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.code.toString();
	}

}
