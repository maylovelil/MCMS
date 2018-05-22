package com.mingsoft.mdiy.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.mdiy.entity.ContentModelFieldEntity;
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
 * @author 姓名 史爱华
 * 
 * @version 300-001-001
 * 
 * <p>
 * 版权所有 MY科技
 * </p>
 *  
 * <p>
 * Comments:字段管理持久化层  || 继承IBaseDao持久化层
 * </p>
 *  
 * <p>
 * Create Date:2014-9-11
 * </p>
 *
 * <p>
 * Modification history:
 * </p>
 */
public interface IContentModelFieldDao extends IBaseDao{

	/**
	 * 查询指定表单的自定义字段列表
	 * @param fieldCmid 表单ID
	 * @return 表单的自定义字段列表
	 */
	public List<BaseEntity> queryListByCmid(int fieldCmid);
	
	/**
	 * 统计指定表单的字段数量
	 * @param fieldCmid 表单ID
	 * @return 表单的字段总数
	 */
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
	public List<BaseEntity> queryByPage(@Param("fieldCmid")int fieldCmid,@Param("pageNo")int pageNo,@Param("pageSize")int pageSize,@Param("orderBy")String orderBy,@Param("order") boolean order);
	
	/**
	 * 根据字段名和表单模型id查找符合条件的记录个数
	 * @param fieldName :字段名
	 * @param fieldCmdId : 表单模型id
	 * @return 记录个数
	 */
	public int getCountFieldName(@Param("fieldFieldName")String fieldFieldName,@Param("fieldCmdId")int fieldCmdId);
	
	/**
	 * 根据表单类型id查找字段列表
	 * @param cmId 表单类型id
	 * @return 字段列表
	 */
	@Deprecated
	public List<BaseEntity> queryListByCmId(@Param("fieldCmId")int fieldCmId);
	
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
	public ContentModelFieldEntity getEntityByCmId(@Param("cmId") int cmId,@Param("fieldFieldName")String fieldFieldName);
	
	/**
	 * 根据自定义字段信息动态查询表
	 * @param table 表名
	 * @param diyFieldName 自定义字段
	 * @return 返回ID集合
	 */
	@Deprecated
	public List<Map> queryListBySQL(@Param("table") String table, @Param("diyFieldName")Map<String,String> diyFieldName);
	
	
	/**
	 * 根据表名和指定条件查询
	 * @param table 自定义字段表名
	 * @param where 指定的查询条件
	 * @return 字段集合
	 */
	@Deprecated
	public List<Map> queryListByListField(@Param("table") String table,@Param("where") String where);
	
	
	/**
	 * 根据内容模型id获取到所有的字段
	 * @param contentModelId 内容模型字段
	 * @return 集合
	 */
	public List<ContentModelFieldEntity> queryByContentModelId(@Param("contentModelId") int contentModelId);
	
	/**
	 * 根据内容模型id和是否搜索查询字段实体列表
	 * @param contentModelId   内容模型id
	 * @param isSearch 是否搜索
	 * @return 字段实体列表
	 */
	@Deprecated
	public List<ContentModelFieldEntity> queryByIsSearch(@Param("contentModelId") Integer contentModelId,@Param("fieldIsSearch")Integer fieldIsSearch);
	
	/**
	 * 根据模型ID删除相应字段
	 * @param fieldCmid 模型ID
	 */
	@Deprecated
	public void deleteEntityByFieldCmid(@Param("fieldCmid") int fieldCmid);
}
