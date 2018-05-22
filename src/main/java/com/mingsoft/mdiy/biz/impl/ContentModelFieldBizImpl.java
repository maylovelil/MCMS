package com.mingsoft.mdiy.biz.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.impl.BasicBizImpl;
import com.mingsoft.mdiy.constant.e.FieldSearchEnum;
import com.mingsoft.mdiy.dao.IContentModelFieldDao;
import com.mingsoft.mdiy.biz.IContentModelFieldBiz;
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
 *          <p>
 *          版权所有 MY科技
 *          </p>
 * 
 *          <p>
 *          Comments:字段业务层实现类，继承BasicBizImpl，实现IFieldBiz
 *          </p>
 * 
 *          <p>
 *          Create Date:2014-9-11
 *          </p>
 *
 *          <p>
 *          Modification history:暂无
 *          </p>
 */
@Service("contentModelFieldBizImpl")
public class ContentModelFieldBizImpl extends BasicBizImpl implements IContentModelFieldBiz {

	/**
	 * 字段持久化层
	 */
	@Autowired
	private IContentModelFieldDao contentModelFieldDao;

	/**
	 * 获取fieldDao
	 */
	@Override
	protected IBaseDao getDao() {
		return contentModelFieldDao;
	}

	/**
	 * 查询指定表单的自定义字段列表
	 * 
	 * @param fieldCmid
	 *            表单ID
	 * @return
	 */
	public List<BaseEntity> queryListByCmid(int fieldCmid) {
		return contentModelFieldDao.queryListByCmid(fieldCmid);
	}

	/**
	 * 统计指定表单的字段数量
	 * 
	 * @param fieldCmid
	 * @return
	 */
	@Deprecated
	public int queryCountByCmid(int fieldCmid) {
		return contentModelFieldDao.queryCountByCmid(fieldCmid);
	}

	/**
	 * 分页查询指定表单的字段
	 * 
	 * @param fieldCmid
	 *            表单ID
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页显示数
	 * @param orderBy
	 *            排序字段
	 * @param order
	 *            排序方式
	 * @return 返回字段集合
	 */
	@Deprecated
	public List<BaseEntity> queryByPage(int fieldCmid, PageUtil page, String orderBy, boolean order) {
		return contentModelFieldDao.queryByPage(fieldCmid, page.getPageNo(), page.getPageSize(), orderBy, order);
	}

	/**
	 * 根据字段名和表单模型id查找符合条件的记录个数
	 * 
	 * @param fieldName
	 *            :字段名
	 * @param fieldCmdId
	 *            : 表单模型id
	 * @return 记录个数
	 */
	@Deprecated
	@Override
	public int getCountFieldName(String fieldFieldName, int fieldCmdId) {
		// TODO Auto-generated method stub
		return contentModelFieldDao.getCountFieldName(fieldFieldName, fieldCmdId);
	}

	/**
	 * 通过字段名获取字段实体
	 * 
	 * @param fieldFieldName
	 *            字段名
	 * @return 字段实体
	 */
	public ContentModelFieldEntity getEntityByFieldName(String fieldFieldName) {
		return contentModelFieldDao.getEntityByFieldName(fieldFieldName);
	}

	/**
	 * 根据内容模型id和字段名查找字段实体
	 * 
	 * @param cmId
	 * @param fieldFieldName
	 * @return 字段实体
	 */
	@Override
	public ContentModelFieldEntity getEntityByCmId(int cmId, String fieldFieldName) {
		return contentModelFieldDao.getEntityByCmId(cmId, fieldFieldName);
	}

	/**
	 * 根据自定义字段信息动态查询表
	 * 
	 * @param table
	 *            表名
	 * @param diyFieldName
	 *            自定义字段
	 * @return 返回ID集合
	 */
	@Deprecated
	public List<Integer> queryListBySQL(String table, Map<String, String> diyFieldName) {

		// 根据列名查找字段实体
		List<ContentModelFieldEntity> listField = new ArrayList<ContentModelFieldEntity>();
		Iterator<String> it = diyFieldName.keySet().iterator();

		for (Iterator iter = diyFieldName.keySet().iterator(); iter.hasNext();) {
			ContentModelFieldEntity field = (ContentModelFieldEntity) contentModelFieldDao
					.getEntityByFieldName(iter.next().toString());
			if (field != null) {
				listField.add(field);
			}
		}

		String where = "where 1=1";
		for (int i = 0; i < listField.size(); i++) {
			for (Iterator iter = diyFieldName.keySet().iterator(); iter.hasNext();) {
				String key = iter.next().toString();
				if (listField.get(i).getFieldFieldName().equals(String.valueOf(key))) {
					// 判断类型是否为数字类型
					if (listField.get(i).getFieldType() == 4 || listField.get(i).getFieldType() == 5) {
						// 判断是否存在数字区间如7-8,如果不存在则进行等值查询
						if (diyFieldName.get(key).indexOf("-") > 0) {
							// 取出“-”的前一位数
							int preNum = 1;
							// 取出“-”的后一位数
							int nextNum = 12;
							where += " and " + listField.get(i).getFieldFieldName() + " between " + preNum + " and "
									+ nextNum;
						} else {
							where += " and " + listField.get(i).getFieldFieldName() + " = "
									+ Integer.valueOf(diyFieldName.get(key));
						}
					} else {
						where += " and " + listField.get(i).getFieldFieldName() + " like " + "'%"
								+ String.valueOf(diyFieldName.get(key)) + "%'";
					}

				}
			}
		}
		List<Map> listMap = contentModelFieldDao.queryListByListField(table, where);
		List<Integer> listIds = new ArrayList<Integer>();
		for (int i = 0; i < listMap.size(); i++) {
			Iterator iter = listMap.get(i).keySet().iterator();
			while (iter.hasNext()) {
				listIds.add(Integer.valueOf(listMap.get(i).get(iter.next()).toString()));
			}
		}

		return listIds;
	}

	@Override
	public List<ContentModelFieldEntity> queryByContentModelId(int contentModelId) {
		return this.contentModelFieldDao.queryByContentModelId(contentModelId);
	}

	/**
	 * 根据模型ID删除对应字段
	 * 
	 * @param fieldCmid
	 *            模型ID
	 */
	@Deprecated
	@Override
	public void deleteEntityByFieldCmid(int fieldCmid) {
		// TODO Auto-generated method stub
		this.contentModelFieldDao.deleteEntityByFieldCmid(fieldCmid);
	}
	@Deprecated
	@Override
	public List<ContentModelFieldEntity> queryByIsSearch(int contentMdoelId, FieldSearchEnum fieldIsSearch) {
		// TODO Auto-generated method stub
		return this.contentModelFieldDao.queryByIsSearch(contentMdoelId, fieldIsSearch.toInt());
	}

}
