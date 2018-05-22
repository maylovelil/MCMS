package com.mingsoft.mdiy.biz;
import java.util.List;
import java.util.Map;

import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.mdiy.constant.e.FieldSearchEnum;
import com.mingsoft.mdiy.entity.ContentModelFieldEntity;
import com.mingsoft.util.PageUtil;

/**
 * 
 * 
 * <p>
 * <b>MYCMS-MY内容管理系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇MY科技有限公司
 * </p>
 * 
 * @author 姓名：张敏
 * 
 * @version 300-001-001
 * 
 * <p>
 * 版权所有 MY科技
 * </p>
 *  
 * <p>
 * Comments:字段业务层，继承IBasicBiz
 * </p>
 *  
 * <p>
 * Create Date:2014-9-11
 * </p>
 *
 * <p>
 * Modification history:暂无
 * </p>
 */
public interface IContentModelFieldBiz extends IBaseBiz{  
	
	/**
	 * checkbox类表单
	 */
	 int CHECKBOX = 11;

	/**
	 * radio类的表单
	 */
	 int RADIO = 10;

	/**
	 * option 类的表单
	 */
	 int OPTION = 9;

	/**
	 * 整数类的表单
	 */
	 int INT = 4;

	/**
	 * float类的表单
	 */
	 int FLOAT = 5;

	
	
	/**
	 * 查询指定表单的自定义字段列表
	 * @param fieldCmid 表单ID
	 * @return
	 */
	public List<BaseEntity> queryListByCmid(int fieldCmid);
	
	/**
	 * 统计指定表单的字段数量
	 * @param fieldCmid
	 * @return
	 */
	@Deprecated
	public int queryCountByCmid(int fieldCmid);
	
	/**
	 * 分页查询指定表单的字段
	 * @param fieldCmid 表单ID
	 * @param pageNo 当前页
	 * @param pageSize 每页显示数
	 * @param orderBy 排序字段
	 * @param order 排序方式
	 * @return 返回字段集合
	 */
	@Deprecated
	public List<BaseEntity> queryByPage(int fieldCmid,PageUtil page,String orderBy,boolean order);
	
	/**
	 * 根据字段名和表单模型id查找符合条件的记录个数
	 * @param fieldName :字段名
	 * @param fieldCmdId : 表单模型id
	 * @return 记录个数
	 */
	@Deprecated
	public int getCountFieldName(String fieldFieldName,int fieldCmdId);
	
	/**
	 * 通过字段名获取字段实体
	 * @param fieldFieldName 字段名
	 * @return 字段实体
	 */
	@Deprecated
	public ContentModelFieldEntity getEntityByFieldName(String fieldFieldName);
	
	/**
	 * 根据内容模型id和字段名查找字段实体
	 * @param cmId
	 * @param fieldFieldName
	 * @return 字段实体
	 */
	public ContentModelFieldEntity getEntityByCmId(int cmId,String fieldFieldName);
	
	/**
	 * 根据自定义字段信息动态查询表
	 * @param table 表名
	 * @param diyFieldName 自定义字段
	 * @return 返回ID集合
	 */
	@Deprecated
	public List<Integer> queryListBySQL(String table,Map<String,String> diyFieldName);
	
	/**
	 * 根据内容模型id获取到所有的字段
	 * @param contentModelId 内容模型字段
	 * @return 字段列表
	 */
	public List<ContentModelFieldEntity> queryByContentModelId(int contentModelId);
	
	/**
	 * 查询需要搜索的字段列表信息
	 * @param contentMdoel 内容模型id
	 * @param fieldIsSearch 是否支持搜索
	 * @return 字段列表信息
	 */
	@Deprecated
	public List<ContentModelFieldEntity> queryByIsSearch(int contentMdoelId,FieldSearchEnum fieldIsSearch);
	
	/**
	 * 根据模型ID删除相应字段
	 * @param fieldCmid 模型ID
	 */
	@Deprecated
	public void deleteEntityByFieldCmid(int fieldCmid);
}
