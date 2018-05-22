package com.mingsoft.mdiy.entity;


import java.util.Date;

import com.mingsoft.basic.entity.BaseEntity;

 /**
 * 字典表实体
 * @author 蓝精灵
 * @version 
 * 版本号：1<br/>
 * 创建日期：2017-8-12 14:22:36<br/>
 * 历史修订：<br/>
 */
public class DictEntity extends BaseEntity {

	private static final long serialVersionUID = 1502518956351L;
	
	/**
	 * 编号
	 */
	private Integer dictId; 
	/**
	 * 应用编号
	 */
	private Integer dictAppId; 
	/**
	 * 数据值
	 */
	private String dictValue; 
	/**
	 * 标签名
	 */
	private String dictLabel; 
	/**
	 * 类型
	 */
	private String dictType; 
	/**
	 * 描述
	 */
	private String dictDescription; 
	/**
	 * 排序（升序）
	 */
	private Integer dictSort; 
	/**
	 * 父级编号
	 */
	private String dictParentId; 
	/**
	 * 创建者
	 */
	private Integer createBy; 
	/**
	 * 创建时间
	 */
	private Date createDate; 
	/**
	 * 更新者
	 */
	private Integer updateBy; 
	/**
	 * 更新时间
	 */
	private Date updateDate; 
	/**
	 * 备注信息
	 */
	private String dictRemarks; 
	/**
	 * 删除标记
	 */
	private Integer del; 
	
	
		
	/**
	 * 设置编号
	 */
	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}

	/**
	 * 获取编号
	 */
	public Integer getDictId() {
		return this.dictId;
	}
	
	/**
	 * 设置应用编号
	 */
	public void setDictAppId(Integer dictAppId) {
		this.dictAppId = dictAppId;
	}

	/**
	 * 获取应用编号
	 */
	public Integer getDictAppId() {
		return this.dictAppId;
	}
	
	/**
	 * 设置数据值
	 */
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	/**
	 * 获取数据值
	 */
	public String getDictValue() {
		return this.dictValue;
	}
	
	/**
	 * 设置标签名
	 */
	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}

	/**
	 * 获取标签名
	 */
	public String getDictLabel() {
		return this.dictLabel;
	}
	
	/**
	 * 设置类型
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	/**
	 * 获取类型
	 */
	public String getDictType() {
		return this.dictType;
	}
	
	/**
	 * 设置描述
	 */
	public void setDictDescription(String dictDescription) {
		this.dictDescription = dictDescription;
	}

	/**
	 * 获取描述
	 */
	public String getDictDescription() {
		return this.dictDescription;
	}
	
	/**
	 * 设置排序（升序）
	 */
	public void setDictSort(Integer dictSort) {
		this.dictSort = dictSort;
	}

	/**
	 * 获取排序（升序）
	 */
	public Integer getDictSort() {
		return this.dictSort;
	}
	
	/**
	 * 设置父级编号
	 */
	public void setDictParentId(String dictParentId) {
		this.dictParentId = dictParentId;
	}

	/**
	 * 获取父级编号
	 */
	public String getDictParentId() {
		return this.dictParentId;
	}
	
	
	/**
	 * 设置备注信息
	 */
	public void setDictRemarks(String dictRemarks) {
		this.dictRemarks = dictRemarks;
	}

	/**
	 * 获取备注信息
	 */
	public String getDictRemarks() {
		return this.dictRemarks;
	}
	
	/**
	 * 设置删除标记
	 */
	public void setDel(Integer del) {
		this.del = del;
	}

	/**
	 * 获取删除标记
	 */
	public Integer getDel() {
		return this.del;
	}
	
}