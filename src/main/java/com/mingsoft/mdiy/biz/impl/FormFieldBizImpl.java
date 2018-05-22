package com.mingsoft.mdiy.biz.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.mdiy.dao.IFormFieldDao;
import com.mingsoft.mdiy.biz.IFormFieldBiz;
import com.mingsoft.mdiy.entity.FormFieldEntity;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;

/**
 * 自定义表单字段接口实现类
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
@Service
public class FormFieldBizImpl extends BaseBizImpl implements IFormFieldBiz {

	/**
	 * 注入自定义表单字段持久化层
	 */
	@Autowired
	private IFormFieldDao diyFormFieldDao;
	
	/**
	 * 获取自定义表单字段持久化层
	 * @return diyFormFieldDao 返回自定义表单字段持久化层
	 */
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return diyFormFieldDao;
	}
	@Override
	public List<FormFieldEntity> queryByDiyFormId(int diyFormId) {
		// TODO Auto-generated method stub
		return diyFormFieldDao.queryByDiyFormId(diyFormId);
	}
	@Override
	public FormFieldEntity getByFieldName(Integer diyFormFormId,
			String diyFormFieldFieldName) {
		// TODO Auto-generated method stub
		return diyFormFieldDao.getByFieldName(diyFormFormId, diyFormFieldFieldName);
	}
	

}
