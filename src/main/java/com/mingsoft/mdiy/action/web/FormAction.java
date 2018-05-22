package com.mingsoft.mdiy.action.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.mdiy.biz.IFormBiz;
import com.mingsoft.base.entity.ListJson;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;

/**
 * 通用自定义表单
 * @author 王天培QQ:78750478
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2012-03-15<br/>
 * 历史修订：<br/>
 */
@Controller("webDiyForm")
@RequestMapping(value={"/mdiy/diyForm","/mdiy/form"})
public class FormAction extends BaseAction{

	/**
	 * 自定义表单业务处理层
	 */
	@Autowired
	IFormBiz diyFormBiz;
	
	/**
	 * 保存
	 * @param idBase64 Base64编码数据
	 * @param request HttpServletRequest对象
	 * @param response HttpServletResponse对象
	 */
	@RequestMapping("{idBase64}")
	@ResponseBody
	public void save(@PathVariable("idBase64") String idBase64,HttpServletRequest request,HttpServletResponse response) {
			String temp =this.decryptByAES(request, idBase64);
			//在进行自定义表单提交数据时是否需要提交验证码，默认是需要验证码
			//如果isCode为空获取，isCode=true，则进行验证码的验证
			if(request.getParameter("rand_code") != null){
			    if (!this.checkRandCode(request)) {
				    this.outJson(response, null,false);
				    return;
			    }
			}
			//判断传入的加密数字是否能转换成整形
			if(!StringUtil.isInteger(temp)){
				this.outJson(response, null, false);
				return;
			}
			//获取表单id
			int  formId = Integer.parseInt(temp);
			if (formId  !=0 ) {
				LOG.debug("fromId:" + formId);
				diyFormBiz.saveDiyFormData(formId,assemblyRequestMap(request));
				this.outJson(response, null, true);
			} 
	}
	/**
	 * 提供前端查询自定义表单提交数据
	 * @param idBase64 Base64编码数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("{idBase64}/queryData")
	@ResponseBody 
	public void queryData(@PathVariable("idBase64") String idBase64,HttpServletRequest request,HttpServletResponse response){
		String temp =this.decryptByAES(request, idBase64);
		//获取自定义表单的id
		int  formId = Integer.parseInt(temp);
		//判断传入的加密数字是否能转换成整形
		if(!StringUtil.isInteger(temp)){
			this.outJson(response, null, false);
			return;
		}
		if (formId  !=0 ) {
			int appId = this.getAppId(request);
			//当前页面
			int pageNo  = this.getPageNo(request);
			//每页显示数量
			int pageSize = this.getInt(request, "pageSize",10);
			//提交记录总数
			int count = diyFormBiz.countDiyFormData(formId, appId);
			PageUtil page = new PageUtil(pageNo,pageSize,count,"");
			Map map = diyFormBiz.queryDiyFormData(formId, appId, page);
			if(map!=null){
				List list = (List) map.get("list");
				ListJson json = new ListJson(count,list);
				this.outJson(response, JSONObject.toJSONString(json));
				return;
			}
		} 
		this.outJson(response, null, false);
	}
	
}
