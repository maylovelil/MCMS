package com.mingsoft.mdiy.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.mdiy.entity.FormEntity;

/**
 * 自定义表单
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public interface IFormDao extends IBaseDao{
	/**
	 * 为自定义表单创建表
	 * @param table 表名
	 * @param fileds 字段集合
	 */
	void createDiyFormTable(@Param("table")String table,@Param("fileds")Map<Object,List> fileds);
	
}
