package com.mingsoft.mdiy.entity;

import com.mingsoft.basic.entity.BaseEntity;
import com.mingsoft.util.AESUtil;
import com.mingsoft.util.StringUtil;
import java.util.Date;

 /**
 * 自定义表单表实体
 * @author 蓝精灵
 * @version 
 * 版本号：1<br/>
 * 创建日期：2017-8-12 15:58:29<br/>
 * 历史修订：<br/>
 */
public class FormEntity extends BaseEntity {

	private static final long serialVersionUID = 1502524709328L;
	
	/**
	 * 自增长id
	 */
	private Integer formId; 
	/**
	 * 自定义表单提示文字
	 */
	private String formTipsName; 
	/**
	 * 自定义表单表名
	 */
	private String formTableName; 
	/**
	 * 自定义表单关联的关联员id
	 */
	private Integer dfManagerid; 
	/**
	 * 自定义表单关联的应用编号
	 */
	private Integer formAppId; 
	/**
	 * 表单的访问地址
	 */
	private String formUrl;
		
	/**
	 * 设置自增长id
	 */
	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	/**
	 * 获取自增长id
	 */
	public Integer getFormId() {
		return this.formId;
	}
	
	/**
	 * 设置自定义表单提示文字
	 */
	public void setFormTipsName(String formTipsName) {
		this.formTipsName = formTipsName;
	}

	/**
	 * 获取自定义表单提示文字
	 */
	public String getFormTipsName() {
		return this.formTipsName;
	}
	
	/**
	 * 设置自定义表单表名
	 */
	public void setFormTableName(String formTableName) {
		this.formTableName = formTableName;
	}

	/**
	 * 获取自定义表单表名
	 */
	public String getFormTableName() {
		return this.formTableName;
	}
	
	/**
	 * 设置自定义表单关联的关联员id
	 */
	public void setDfManagerid(Integer dfManagerid) {
		this.dfManagerid = dfManagerid;
	}

	/**
	 * 获取自定义表单关联的关联员id
	 */
	public Integer getDfManagerid() {
		return this.dfManagerid;
	}
	
	/**
	 * 设置自定义表单关联的应用编号
	 */
	public void setFormAppId(Integer formAppId) {
		this.formAppId = formAppId;
	}

	/**
	 * 获取自定义表单关联的应用编号
	 */
	public Integer getFormAppId() {
		return this.formAppId;
	}
	public String getFormUrl() {
		formUrl=AESUtil.encrypt(this.formId+"", StringUtil.Md5(this.getAppId()+"").substring(16));
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

}