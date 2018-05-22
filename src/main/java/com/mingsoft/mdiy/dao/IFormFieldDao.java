package com.mingsoft.mdiy.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.mdiy.entity.FormFieldEntity;

/**
 * 自定义表单字段
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
public interface IFormFieldDao extends IBaseDao{

	/**
	 * 通过from的id获取实体
	 * @param diyFormId　自定义表单id 
	 * @return　返回实体
	 */
	List<FormFieldEntity> queryByDiyFormId(@Param("diyFormFieldFormId") int diyFormId);
	
	/**
	 * 获取自定义表单字段
	 * @param diyFormId　自定义表单id 
	 * @param diyFormFieldFieldName 　自定义表单字段名
	 * @return 返回自定义表单实体
	 */
	FormFieldEntity getByFieldName(@Param("diyFormFieldFormId") Integer diyFormId,@Param("diyFormFieldFieldName") String  diyFormFieldFieldName);
}
