package net.mingsoft.basic.bean;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 城市数据格式bean
 * @author qiu
 *
 */

public class CityBean {
	
	
	/**
	 * 城市id
	 */
	private Long id;
	
	/**
	 * 城市名称
	 */
	private String name;
	
	/**
	 * 父级id
	 */
	private Long parentId;
	
	/**
	 * 子城市数据集合
	 */
	private List<CityBean> childrensList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<CityBean> getChildrensList() {
		return childrensList;
	}

	public void setChildrensList(List<CityBean> childrensList) {
		this.childrensList = childrensList;
	}

}