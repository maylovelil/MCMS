package com.mingsoft.mdiy.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.mdiy.constant.e.DiyFormFieldEnum;
import com.mingsoft.mdiy.dao.IFormFieldDao;
import com.mingsoft.mdiy.entity.FormEntity;
import com.mingsoft.mdiy.biz.IFormBiz;
import com.mingsoft.mdiy.dao.IFormDao;
import com.mingsoft.mdiy.entity.FormFieldEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 自定义表单接口实现类
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
@Service
public class FormBizImpl extends BaseBizImpl implements IFormBiz {

	/**
	 * 自定义表单的默认字段
	 */
	private static final String FORM_ID = "fromID";
	
	private static final String DATE = "date";
	
	/**
	 * 自定义表单生成的表自增长编号
	 */
	private static final String  ID = "id";
	
	/**
	 * 注入自定义表单持久化层
	 */
	@Autowired
	private IFormDao formDao;
	
	/**
	 * 自定义表单字段持久化层的注入
	 */
	@Autowired
	private IFormFieldDao formFieldDao;

	/**
	 * 获取类别持久化层
	 * @return diyFormDao 返回类别持久话层
	 */
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return formDao;
	}

	@Override
	public void saveDiyFormData(int formId, Map params) {
		// TODO Auto-generated method stub
		FormEntity dfe = (FormEntity) formDao.getEntity(formId);
		if (dfe == null) {
			return;
		}
		String tableName = dfe.getFormTableName();
		List<FormFieldEntity> filedList = formFieldDao.queryByDiyFormId(formId);
		if (filedList == null) {
			return;
		}
		Map values = builderSqlMap(filedList,params);
		values.put(FORM_ID, formId);
		values.put(DATE, new Date());
		formFieldDao.insertBySQL(tableName,values);
	}

	@Override
	public Map queryDiyFormData(int diyFormId,int appId,PageUtil page) {
		// TODO Auto-generated method stub
		FormEntity dfe = (FormEntity) formDao.getEntity(diyFormId);
		if (dfe!=null) {
			List<FormFieldEntity> fieldList = formFieldDao.queryByDiyFormId(diyFormId);
			List<Map<String,String>> fields = new ArrayList<Map<String,String>>();
			for (int i=0;i<fieldList.size();i++) {
				Map<String,String> field = new HashMap<String,String>();
				field.put(fieldList.get(i).getDiyFormFieldFieldName(), fieldList.get(i).getDiyFormFieldTipsName());
				fields.add(field);
			}
			Map wheres = new HashMap();
			wheres.put(FORM_ID, diyFormId);
			List list = formDao.queryBySQL(dfe.getFormTableName(), null, wheres, page.getPageSize()*page.getPageNo(), page.getPageSize(),ID);
			Map r = new HashMap();
			r.put("fields", fieldList);
			r.put("list", list);
			return r;
		}
		
		return null;
	}


	@Override
	public void deleteQueryDiyFormData(int id,int diyFormId) {
		// TODO Auto-generated method stub
		FormEntity dfe = (FormEntity) formDao.getEntity(diyFormId);
		Map wheres = new HashMap();
		wheres.put(FORM_ID, diyFormId);
		wheres.put(ID, id);
		formDao.deleteBySQL(dfe.getFormTableName(), wheres);
	}

	@Override
	public int countDiyFormData(int diyFormId, int appId) {
		// TODO Auto-generated method stub
		FormEntity dfe = (FormEntity) formDao.getEntity(diyFormId);
		Map wheres = new HashMap();
		wheres.put(FORM_ID, diyFormId);
		return formDao.countBySQL(dfe.getFormTableName(),wheres );
	}

	/**
	 * 遍历出所有字段的信息
	 * 
	 * @param listField 字段列表
	 * @param params 请求参数
	 * @return 返回所有字段的信息
	 */
	private Map builderSqlMap(List listField, Map params) {
		Map mapParams = new HashMap();
		// 遍历字段名
		for (int i = 0; i < listField.size(); i++) {
			FormFieldEntity field = (FormFieldEntity) listField.get(i);
			String fieldName = field.getDiyFormFieldFieldName();
//			int fieldType = field.getDiyFormFieldType();
//			if (fieldType == DiyFormFieldEnum. ) {
//				String langtyp[] = (String[])params.get(fieldName);
//				if (langtyp != null) {
//					StringBuffer sb = new StringBuffer();
//					for (int j = 0; j < langtyp.length; j++) {
//						sb.append(langtyp[j] + ",");
//					}
//					mapParams.put(fieldName, sb.toString());
//				} else {
//					mapParams.put(fieldName, langtyp);
//				}
//			} else {
				if (StringUtil.isBlank(params.get(fieldName))) {
					mapParams.put(fieldName, null);
				} else {
					mapParams.put(fieldName, params.get(fieldName));
				}
			//			}
		}
		return mapParams;
	}

	@Override
	public void createDiyFormTable(String table, Map<Object, List> fileds) {
		// TODO Auto-generated method stub
		formDao.createDiyFormTable(table, fileds);
	}

}
