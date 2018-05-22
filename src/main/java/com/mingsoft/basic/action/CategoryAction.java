package com.mingsoft.basic.action;

import java.sql.Timestamp;
import java.util.List;

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
import com.mingsoft.basic.biz.ICategoryBiz;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.CategoryEntity;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;

import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;

import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 分类表管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：0.0<br/>
 * 创建日期：2017-8-22 10:03:38<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/category")
public class CategoryAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入分类表业务层
	 */	
	@Autowired
	private ICategoryBiz categoryBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	@RequiresPermissions("category:view")
	public String index(HttpServletResponse response,HttpServletRequest request,ModelMap model){
		String modelTitle = BasicUtil.getString("modelTitle");
		int modelId =BasicUtil.getInt("modelId");
		model.addAttribute("modelTitle",modelTitle);
		model.addAttribute("modelId",modelId);
		return view ("/category/index");
	}
	
	/**
	 * 查询分类表列表
	 * @param category 分类表实体
	 * <i>category参数包含字段信息参考：</i><br/>
	 * categoryId 类别ID<br/>
	 * categoryTitle 类别标题<br/>
	 * categorySort 类别排序<br/>
	 * categoryDatetime 类别发布时间<br/>
	 * categoryManagerid 发布用户ID<br/>
	 * categoryModelid 所属模块ID<br/>
	 * categoryCategoryid 父类别编号<br/>
	 * categorySmallimg 略缩图<br/>
	 * categoryAppid 应用编号<br/>
	 * categoryDescription 栏目描述<br/>
	 * categoryParentId 父类型编号<br/>
	 * categoryDictId 字典对应编号<br/>
	 * createBy 创建人<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新人<br/>
	 * updateDate 更新时间<br/>
	 * del 删除状态<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * categoryId: 类别ID<br/>
	 * categoryTitle: 类别标题<br/>
	 * categorySort: 类别排序<br/>
	 * categoryDatetime: 类别发布时间<br/>
	 * categoryManagerid: 发布用户ID<br/>
	 * categoryModelid: 所属模块ID<br/>
	 * categoryCategoryid: 父类别编号<br/>
	 * categorySmallimg: 略缩图<br/>
	 * categoryAppid: 应用编号<br/>
	 * categoryDescription: 栏目描述<br/>
	 * categoryParentId: 父类型编号<br/>
	 * categoryDictId: 字典对应编号<br/>
	 * createBy: 创建人<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新人<br/>
	 * updateDate: 更新时间<br/>
	 * del: 删除状态<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute CategoryEntity category,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		String modelId = request.getParameter("modelId");
		this.setSession(request, SessionConstEnum.MANAGER_MODEL_CODE, modelId);
		// 获取登录的session
		ManagerSessionEntity managerSession = (ManagerSessionEntity) getManagerBySession(request);
		// 传入一个实体，提供查询条件
		category.setCategoryModelId(Integer.parseInt(modelId));
 
		AppEntity app = this.getApp(request);
		// 查询指定的appId下的分类
		category.setCategoryAppId(app.getAppId());
		// 判断是否为该网站总管理员，如果是管理员查询分类时则可以不受管理员限制，即可以查看所有的分类
		if (managerSession.getManagerId() != app.getAppManagerId()) {
			category.setCategoryManagerId(managerSession.getManagerId());
		}
		BasicUtil.startPage();
		List categoryList = categoryBiz.query(category);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(categoryList,(int)BasicUtil.endPage(categoryList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面category_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute CategoryEntity category,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(category.getCategoryId() > 0){
			BaseEntity categoryEntity = categoryBiz.getEntity(category.getCategoryId());			
			model.addAttribute("categoryEntity",categoryEntity);
		}
		request.setAttribute("modelTitle", BasicUtil.getString("modelTitle"));
		request.setAttribute("modelId", BasicUtil.getInt("modelId"));
		return view ("/category/form");
	}
	
	/**
	 * 获取分类表
	 * @param category 分类表实体
	 * <i>category参数包含字段信息参考：</i><br/>
	 * categoryId 类别ID<br/>
	 * categoryTitle 类别标题<br/>
	 * categorySort 类别排序<br/>
	 * categoryDatetime 类别发布时间<br/>
	 * categoryManagerid 发布用户ID<br/>
	 * categoryModelid 所属模块ID<br/>
	 * categoryCategoryid 父类别编号<br/>
	 * categorySmallimg 略缩图<br/>
	 * categoryAppid 应用编号<br/>
	 * categoryDescription 栏目描述<br/>
	 * categoryParentId 父类型编号<br/>
	 * categoryDictId 字典对应编号<br/>
	 * createBy 创建人<br/>
	 * createDate 创建时间<br/>
	 * updateBy 更新人<br/>
	 * updateDate 更新时间<br/>
	 * del 删除状态<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * categoryId: 类别ID<br/>
	 * categoryTitle: 类别标题<br/>
	 * categorySort: 类别排序<br/>
	 * categoryDatetime: 类别发布时间<br/>
	 * categoryManagerid: 发布用户ID<br/>
	 * categoryModelid: 所属模块ID<br/>
	 * categoryCategoryid: 父类别编号<br/>
	 * categorySmallimg: 略缩图<br/>
	 * categoryAppid: 应用编号<br/>
	 * categoryDescription: 栏目描述<br/>
	 * categoryParentId: 父类型编号<br/>
	 * categoryDictId: 字典对应编号<br/>
	 * createBy: 创建人<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新人<br/>
	 * updateDate: 更新时间<br/>
	 * del: 删除状态<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute CategoryEntity category,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(category.getCategoryId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("category.id")));
			return;
		}
		CategoryEntity _category = (CategoryEntity)categoryBiz.getEntity(category.getCategoryId());
		this.outJson(response, _category);
	}
	
	/**
	 * 保存分类表实体
	 * @param category 分类表实体
	 * <i>category参数包含字段信息参考：</i><br/>
	 * categoryId 类别ID<br/>
	 * categoryTitle 类别标题<br/>
	 * categorySmallimg 略缩图<br/>
	 * categoryDescription 栏目描述<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * categoryId: 类别ID<br/>
	 * categoryTitle: 类别标题<br/>
	 * categorySort: 类别排序<br/>
	 * categoryDatetime: 类别发布时间<br/>
	 * categoryManagerid: 发布用户ID<br/>
	 * categoryModelid: 所属模块ID<br/>
	 * categoryCategoryid: 父类别编号<br/>
	 * categorySmallimg: 略缩图<br/>
	 * categoryAppid: 应用编号<br/>
	 * categoryDescription: 栏目描述<br/>
	 * categoryParentId: 父类型编号<br/>
	 * categoryDictId: 字典对应编号<br/>
	 * createBy: 创建人<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新人<br/>
	 * updateDate: 更新时间<br/>
	 * del: 删除状态<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@RequiresPermissions("category:save")
	@ResponseBody
	public void save(@ModelAttribute CategoryEntity category, HttpServletResponse response, HttpServletRequest request) {
		//验证类别标题的值是否合法			
		if(StringUtil.isBlank(category.getCategoryTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("category.title")));
			return;			
		}
		if(!StringUtil.checkLength(category.getCategoryTitle()+"", 1, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("category.title"), "1", "50"));
			return;			
		}
		//验证栏目描述的值是否合法			
		if(!StringUtil.checkLength(category.getCategoryDescription()+"", 0, 45)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("category.description"), "1", "45"));
			return;			
		}
		category.setCategoryManagerId(((ManagerEntity) getManagerBySession(request)).getManagerId());
		category.setCategoryDateTime(new Timestamp(System.currentTimeMillis()));
		category.setCategoryAppId(BasicUtil.getAppId());
		categoryBiz.saveEntity(category);
		this.outJson(response, JSONObject.toJSONString(category));
	}
	
	/**
	 * @param category 分类表实体
	 * <i>category参数包含字段信息参考：</i><br/>
	 * categoryId:多个categoryId直接用逗号隔开,例如categoryId=1,2,3,4
	 * 批量删除分类表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("category:delete")
	@ResponseBody
	public void delete(@RequestBody List<CategoryEntity> categorys,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[categorys.size()];
		for(int i = 0;i<categorys.size();i++){
			ids[i] = categorys.get(i).getCategoryId();
		}
		categoryBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新分类表信息分类表
	 * @param category 分类表实体
	 * <i>category参数包含字段信息参考：</i><br/>
	 * categoryId 类别ID<br/>
	 * categoryTitle 类别标题<br/>
	 * categorySmallimg 略缩图<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * categoryId: 类别ID<br/>
	 * categoryTitle: 类别标题<br/>
	 * categorySort: 类别排序<br/>
	 * categoryDatetime: 类别发布时间<br/>
	 * categoryManagerid: 发布用户ID<br/>
	 * categoryModelid: 所属模块ID<br/>
	 * categoryCategoryid: 父类别编号<br/>
	 * categorySmallimg: 略缩图<br/>
	 * categoryAppid: 应用编号<br/>
	 * categoryDescription: 栏目描述<br/>
	 * categoryParentId: 父类型编号<br/>
	 * categoryDictId: 字典对应编号<br/>
	 * createBy: 创建人<br/>
	 * createDate: 创建时间<br/>
	 * updateBy: 更新人<br/>
	 * updateDate: 更新时间<br/>
	 * del: 删除状态<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@RequiresPermissions("category:update")
	@ResponseBody	 
	public void update(@ModelAttribute CategoryEntity category, HttpServletResponse response,
			HttpServletRequest request) {
		//验证类别标题的值是否合法			
		if(StringUtil.isBlank(category.getCategoryTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("category.title")));
			return;			
		}
		if(!StringUtil.checkLength(category.getCategoryTitle()+"", 1, 50)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("category.title"), "1", "50"));
			return;			
		}
		//验证栏目描述的值是否合法			
		if(!StringUtil.checkLength(category.getCategoryDescription()+"", 0, 45)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("category.description"), "1", "45"));
			return;			
		}
		category.setCategoryManagerId(((ManagerEntity) getManagerBySession(request)).getManagerId());
		categoryBiz.updateEntity(category);
		this.outJson(response, JSONObject.toJSONString(category));
	}
	
//	/**
//	 * 根据分类id查找分类实体和它的父分类
//	 * 
//	 * @param categoryId
//	 * @param request
//	 * @param mode
//	 * @param response
//	 */
//	/**
//	 * 根据分类id查找分类实体和它的父分类
//	 * 
//	 * @param categoryId
//	 *            栏目id
//	 * @param request
//	 *            请求对象
//	 * @param mode
//	 * @param response
//	 */
//	@RequestMapping("/{categoryId}/query")
//	public void query(@PathVariable int categoryId, HttpServletRequest request, ModelMap mode,
//			HttpServletResponse response) {
//		// 根据分类id查询分类实体
//		CategoryEntity category = (CategoryEntity) categoryBiz.getEntity(categoryId);
//		// 如何分类实体不存在
//		if (category == null) {
//			return;
//		}
//		// 查询该分类的父分类
//		CategoryEntity categoryCategory = (CategoryEntity) categoryBiz.getEntity(category.getCategoryCategoryId());
//
//		List<CategoryEntity> list = new ArrayList<CategoryEntity>();
//		list.add(categoryCategory);
//		list.add(category);
//		this.outJson(response, JSONObject.toJSONString(list));
//	}

	
	
	/**
	 * 根据分类id查找分类子分类
	 * 
	 * @param categoryId
	 * @param request
	 * @param mode
	 * @param response
	 */
	@RequestMapping("/{categoryId}/queryChildren")
	public void queryChildren(@PathVariable int categoryId, HttpServletRequest request, ModelMap mode,
			HttpServletResponse response) {
		CategoryEntity category = (CategoryEntity) this.categoryBiz.getEntity(categoryId);
		if (category != null) {
			List<CategoryEntity> list = this.categoryBiz.queryChilds(category);
			this.outJson(response, JSONObject.toJSONString(list));
		}

	}

//	/**
//	 * 加载栏目列表页并查询所有栏目下子栏目
//	 * 
//	 * @param categoryId
//	 *            栏目id
//	 * @param request
//	 *            请求对象
//	 * @param response
//	 *            响应对象
//	 * @return 栏目列表地址
//	 */
//	@RequestMapping("/{categoryId}/childList")
//	public String childList(@PathVariable int categoryId, HttpServletRequest request, HttpServletResponse response) {
//		String modelId = request.getParameter("modelId");
//		String categoryCategoryId = request.getParameter("categoryCategoryId");// 提供展开效果使用
//		this.setSession(request, SessionConstEnum.MANAGER_MODEL_CODE, modelId);
//		// 获取登录的session
//		ManagerSessionEntity managerSession = (ManagerSessionEntity) getManagerBySession(request);
//		// 传入一个实体，提供查询条件
//		CategoryEntity category = new CategoryEntity();
//		category.setCategoryModelId(Integer.parseInt(modelId));
//
//		AppEntity app = this.getApp(request);
//		int appId = app.getAppId();
//		// 查询指定的appId下的分类
//		category.setCategoryAppId(appId);
//		// 判断是否为该网站总管理员，如果是管理员查询分类时则可以不受管理员限制，即可以查看所有的分类
//		if (managerSession.getManagerId() != app.getAppManagerId()) {
//			category.setCategoryManagerId(managerSession.getManagerId());
//		}
//
//		List<CategoryEntity> list = categoryBiz.queryChildrenCategory(categoryId, appId, this.getModelCodeId(request));
//
//		// 保存cookie值
//		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE, "/manager/category/list.do");
//		request.setAttribute("categoryCategoryId", categoryCategoryId);
//		request.setAttribute("categoryJson", JSONArray.toJSONString(list));
//		request.setAttribute("modelId", request.getParameter("modelId"));
//		return Const.VIEW + "/category/category_list";
//	}
		
}