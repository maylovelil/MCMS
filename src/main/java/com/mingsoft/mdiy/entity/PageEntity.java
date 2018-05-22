package com.mingsoft.mdiy.entity;

import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 自定义页面表实体
 * @author 蓝精灵
 * @version 
 * 版本号：1<br/>
 * 创建日期：2017-8-11 14:01:54<br/>
 * 历史修订：<br/>
 */
public class PageEntity extends BaseEntity {

	private static final long serialVersionUID = 1502431314331L;
	
	/**
	 * 自增长id
	 */
	private Integer pageId; 
	/**
	 * 模块id
	 */
	private Integer pageModelId; 
	/**
	 * 应用id
	 */
	private Integer pageAppId; 
	/**
	 * 自定义页面绑定模板的路径
	 */
	private String pagePath; 
	/**
	 * 自定义页面标题
	 */
	private String pageTitle; 
	/**
	 * 自定义页面访问路径
	 */
	private String pageKey; 
	
	
		
	/**
	 * 设置自增长id
	 */
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	/**
	 * 获取自增长id
	 */
	public Integer getPageId() {
		return this.pageId;
	}
	
	/**
	 * 设置模块id
	 */
	public void setPageModelId(Integer pageModelId) {
		this.pageModelId = pageModelId;
	}

	/**
	 * 获取模块id
	 */
	public Integer getPageModelId() {
		return this.pageModelId;
	}
	
	/**
	 * 设置应用id
	 */
	public void setPageAppId(Integer pageAppId) {
		this.pageAppId = pageAppId;
	}

	/**
	 * 获取应用id
	 */
	public Integer getPageAppId() {
		return this.pageAppId;
	}
	
	/**
	 * 设置自定义页面绑定模板的路径
	 */
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	/**
	 * 获取自定义页面绑定模板的路径
	 */
	public String getPagePath() {
		return this.pagePath;
	}
	
	/**
	 * 设置自定义页面标题
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * 获取自定义页面标题
	 */
	public String getPageTitle() {
		return this.pageTitle;
	}
	
	/**
	 * 设置自定义页面访问路径
	 */
	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

	/**
	 * 获取自定义页面访问路径
	 */
	public String getPageKey() {
		return this.pageKey;
	}
	
}