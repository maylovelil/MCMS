package com.mingsoft.mdiy.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mingsoft.mdiy.biz.IFormBiz;
import com.mingsoft.mdiy.biz.IFormFieldBiz;
import com.mingsoft.mdiy.entity.FormEntity;
import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.bean.ListBean;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.ManagerEntity;

import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 自定义表单表管理控制层
 * @author 蓝精灵
 * @version 
 * 版本号：1<br/>
 * 创建日期：2017-8-12 15:58:29<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/mdiy/form")
public class FormAction extends BaseAction{
	
	/**
	 * 自定义表前缀
	 */
	private static final String TABLE_NAME_PREFIX = "mdiy_";
	
	/**
	 * 表名分隔符
	 */
	private static final String TABLE_NAME_SPLIT= "_";
	
	/**
	 * 注入自定义表单biz
	 */
	@Autowired
	IFormBiz formBiz;
	
	/**
	 * 注入自定义表单字段的biz
	 */
	@Autowired
	IFormFieldBiz formFieldBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/mdiy/form/index");
	}
	
	/**
	 * 查询自定义表单表列表
	 * @param form 自定义表单表实体
	 * <i>form参数包含字段信息参考：</i><br/>
	 * formId 自增长id<br/>
	 * formTipsName 自定义表单提示文字<br/>
	 * formTableName 自定义表单表名<br/>
	 * dfManagerid 自定义表单关联的关联员id<br/>
	 * formAppId 自定义表单关联的应用编号<br/>
	 * createBy 创建者<br/>
	 * updateBy 更新者<br/>
	 * createDate 创建时间<br/>
	 * updateDate 更新时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * formId: 自增长id<br/>
	 * formTipsName: 自定义表单提示文字<br/>
	 * formTableName: 自定义表单表名<br/>
	 * dfManagerid: 自定义表单关联的关联员id<br/>
	 * formAppId: 自定义表单关联的应用编号<br/>
	 * createBy: 创建者<br/>
	 * updateBy: 更新者<br/>
	 * createDate: 创建时间<br/>
	 * updateDate: 更新时间<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute FormEntity form,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		form.setAppId(BasicUtil.getAppId());
		BasicUtil.startPage();
		List formList = formBiz.query(form);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(formList,(int)BasicUtil.endPage(formList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面form_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute FormEntity form,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(form.getFormId() != null){
			FormEntity formEntity = (FormEntity) formBiz.getEntity(form.getFormId());			
			model.addAttribute("formEntity",formEntity);
		}
		
		return view ("/mdiy/form/form");
	}
	
	/**
	 * 获取自定义表单表
	 * @param form 自定义表单表实体
	 * <i>form参数包含字段信息参考：</i><br/>
	 * formId 自增长id<br/>
	 * formTipsName 自定义表单提示文字<br/>
	 * formTableName 自定义表单表名<br/>
	 * dfManagerid 自定义表单关联的关联员id<br/>
	 * formAppId 自定义表单关联的应用编号<br/>
	 * createBy 创建者<br/>
	 * updateBy 更新者<br/>
	 * createDate 创建时间<br/>
	 * updateDate 更新时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * formId: 自增长id<br/>
	 * formTipsName: 自定义表单提示文字<br/>
	 * formTableName: 自定义表单表名<br/>
	 * dfManagerid: 自定义表单关联的关联员id<br/>
	 * formAppId: 自定义表单关联的应用编号<br/>
	 * createBy: 创建者<br/>
	 * updateBy: 更新者<br/>
	 * createDate: 创建时间<br/>
	 * updateDate: 更新时间<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute FormEntity form,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(form.getFormId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("form.id")));
			return;
		}
		FormEntity _form = (FormEntity)formBiz.getEntity(form.getFormId());
		this.outJson(response, _form);
	}
	
	/**
	 * @param form 自定义表单表实体
	 * <i>form参数包含字段信息参考：</i><br/>
	 * formId:多个formId直接用逗号隔开,例如formId=1,2,3,4
	 * 批量删除自定义表单表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestBody List<FormEntity> forms,HttpServletResponse response, HttpServletRequest request) {
		for(int i = 0;i<forms.size();i++){
			//根据表单id查找表单实体
			FormEntity form = (FormEntity) formBiz.getEntity(forms.get(i).getFormId());
			if(form==null){
				this.outJson(response, null, false,this.getResString("err.not.exist",this.getResString("diy.form")));
				return;
			}
			formBiz.dropTable(form.getFormTableName());
			formBiz.deleteEntity(forms.get(i).getFormId());
		}
		this.outJson(response, true);
	}
	
	
	/**
	 * 保存自定义表单表实体
	 * @param form 自定义表单表实体
	 * <i>form参数包含字段信息参考：</i><br/>
	 * formId 自增长id<br/>
	 * formTipsName 自定义表单提示文字<br/>
	 * formTableName 自定义表单表名<br/>
	 * dfManagerid 自定义表单关联的关联员id<br/>
	 * formAppId 自定义表单关联的应用编号<br/>
	 * createBy 创建者<br/>
	 * updateBy 更新者<br/>
	 * createDate 创建时间<br/>
	 * updateDate 更新时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * formId: 自增长id<br/>
	 * formTipsName: 自定义表单提示文字<br/>
	 * formTableName: 自定义表单表名<br/>
	 * dfManagerid: 自定义表单关联的关联员id<br/>
	 * formAppId: 自定义表单关联的应用编号<br/>
	 * createBy: 创建者<br/>
	 * updateBy: 更新者<br/>
	 * createDate: 创建时间<br/>
	 * updateDate: 更新时间<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/save")
	@RequiresPermissions("mdiy:form:save")
	public void save(@ModelAttribute FormEntity form,HttpServletRequest request, HttpServletResponse response){
		// 更新前判断数据是否合法
		if(!StringUtil.checkLength(form.getFormTableName(), 1,20)){
				this.outJson(response, null, false,getResString("err.length",this.getResString("fieldTipsName"),"1","20"));
				return ;
		}
		if(!StringUtil.checkLength(form.getFormTipsName(), 1,20)){
				this.outJson(response, null, false,getResString("err.length",this.getResString("fieldFieldName"),"1","20"));
				return ;
		}
		
		// 获取当前管理员实体
		ManagerEntity managerSession = (ManagerEntity) getSession(request, SessionConstEnum.MANAGER_SESSION);
		//获取当前管理员Id
		int managerId = managerSession.getManagerId();
		//获取实际创建的表名
		String formTableName =TABLE_NAME_PREFIX+form.getFormTableName()+TABLE_NAME_SPLIT+managerId;
		FormEntity _form = new FormEntity();
		_form.setFormTableName(formTableName);
		//判断是否存在重复的表
		if( formBiz.getEntity(_form)!=null){
			this.outJson(response, null, false,this.getResString("err.exist",this.getResString("diy.form.table.name")));
			return;
		}
		//获取appId
		int appId = this.getAppId(request);
		//设置appId
		form.setAppId(BasicUtil.getAppId());
		//设置自定义表单的表面
		String tableName = TABLE_NAME_PREFIX+form.getFormTableName()+TABLE_NAME_SPLIT+managerId;
		form.setFormTableName(tableName);
		
		//在数据库中创建自定义表单的表
		formBiz.createDiyFormTable(form.getFormTableName(), null);
		//保存自定义表单实体
		formBiz.saveEntity(form);
		_form.setAppId(BasicUtil.getAppId());
		_form = (FormEntity) formBiz.getEntity(_form);
		int diyFormId =  _form.getFormId();
		this.outJson(response, null, true,String.valueOf(diyFormId));
	}
	
	/** 
	 * 更新自定义表单表信息自定义表单表
	 * @param form 自定义表单表实体
	 * <i>form参数包含字段信息参考：</i><br/>
	 * formId 自增长id<br/>
	 * formTipsName 自定义表单提示文字<br/>
	 * formTableName 自定义表单表名<br/>
	 * dfManagerid 自定义表单关联的关联员id<br/>
	 * formAppId 自定义表单关联的应用编号<br/>
	 * createBy 创建者<br/>
	 * updateBy 更新者<br/>
	 * createDate 创建时间<br/>
	 * updateDate 更新时间<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * formId: 自增长id<br/>
	 * formTipsName: 自定义表单提示文字<br/>
	 * formTableName: 自定义表单表名<br/>
	 * dfManagerid: 自定义表单关联的关联员id<br/>
	 * formAppId: 自定义表单关联的应用编号<br/>
	 * createBy: 创建者<br/>
	 * updateBy: 更新者<br/>
	 * createDate: 创建时间<br/>
	 * updateDate: 更新时间<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/update")
	@RequiresPermissions("mdiy:form:update")
	public void update(@ModelAttribute FormEntity form, HttpServletResponse response,HttpServletRequest request){
		// 更新前判断数据是否合法
		if(!StringUtil.checkLength(form.getFormTableName(), 1,20)){
				this.outJson(response, null, false,getResString("err.length",this.getResString("fieldTipsName"),"1","20"));
				return ;
		}
		if(!StringUtil.checkLength(form.getFormTipsName(), 1,20)){
				this.outJson(response, null, false,getResString("err.length",this.getResString("fieldFieldName"),"1","20"));
				return ;
		}
		FormEntity _form = new FormEntity();
		_form.setFormTableName(form.getFormTableName());
		_form.setAppId(BasicUtil.getAppId());
		_form = (FormEntity) formBiz.getEntity(_form);
		//自增长ID
		int formId =  _form.getFormId();
		form.setFormId(formId);
		//更新自定义表单实体
		formBiz.updateEntity(form);
		
		this.outJson(response, null, true,String.valueOf(formId));
	}
	
	/**
	 * 验证自定义表名合法性
	 * @param formTableName 自定义表单表名
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/checkTableNameExist")
	public void checkTableNameExist(@ModelAttribute FormEntity form, HttpServletRequest request, HttpServletResponse response){
		// 获取当前管理员实体
		ManagerEntity managerSession = (ManagerEntity) getSession(request, SessionConstEnum.MANAGER_SESSION);
		//获取当前管理员Id
		int managerId = managerSession.getManagerId();
		//组装表名
		String formTableName =TABLE_NAME_PREFIX+form.getFormTableName()+TABLE_NAME_SPLIT+managerId;
		FormEntity _form = new FormEntity();
		_form.setFormTableName(formTableName);
		_form = (FormEntity) formBiz.getEntity(_form);
		if(_form==null){
			this.outJson(response, null, false);
			return;
		}
		this.outJson(response, null, true);
	}
	/**
	 * 加载自定义表单的数据列表页面
	 * @param diyFormId 自定义表单id
	 * @param request 请求对象
	 * @param model ModelMap实体对象
	 * @return 自定义表单数据列表页面地址
	 */
	@RequestMapping("/querydata")
	public String query(@ModelAttribute FormEntity form,HttpServletRequest request,ModelMap model)  {
		// 当前页面
		int pageNo = 1;
		// 获取页面的当页数
		if (request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		int appId = BasicUtil.getAppId();
		int count = formBiz.countDiyFormData(form.getFormId(), appId);
		PageUtil page = new PageUtil(pageNo, 30,count,"/manager/diy_form/"+form.getFormId()+"/query.do");
		Map map = formBiz.queryDiyFormData(form.getFormId(), appId, page);
		if (map!=null) {
			if (map.get("fields") != null) {
				model.addAttribute("fields", map.get("fields"));
			}
			if (map.get("list") != null) {
				model.addAttribute("list", map.get("list"));
			}			
		}
		
		model.addAttribute("title", request.getParameter("title"));
		model.addAttribute("page", page);
		return view("/mdiy/diy_form/diy_form_data_list");
	}
	
	/**
	 * 根据id删除自定义表单
	 * @param id 自定义表单的自增长id
	 * @param diyFormId 自定义表单id
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/{diyFormId}/{id}/delete")
	@ResponseBody
	@RequiresPermissions("mdiy:form:del")
	public void delete(@PathVariable("id") int id,@PathVariable("diyFormId") int diyFormId,HttpServletRequest request,HttpServletResponse response)  {
		formBiz.deleteQueryDiyFormData(id, diyFormId);
		this.outJson(response, null, true);
	}
}
