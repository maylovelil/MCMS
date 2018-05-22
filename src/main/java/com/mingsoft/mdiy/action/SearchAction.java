package com.mingsoft.mdiy.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mingsoft.mdiy.biz.IContentModelFieldBiz;
import com.mingsoft.mdiy.biz.ISearchBiz;
import com.mingsoft.mdiy.entity.ContentModelFieldEntity;
import com.mingsoft.mdiy.entity.SearchEntity;
import net.mingsoft.base.util.JSONArray;
import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.mingsoft.basic.biz.IColumnBiz;
import com.mingsoft.basic.entity.ColumnEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
	
/**
 * 自定义搜索表管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-7-19 9:43:06<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/mdiy/search")
public class SearchAction extends com.mingsoft.mdiy.action.BaseAction{
	
	/**
	 * 注入自定义搜索表业务层
	 */	
	@Autowired
	private ISearchBiz searchBiz;
	
	@Autowired
	private IColumnBiz columnBiz;
	
	/**
	 * 字段业务层
	 */
	@Autowired
	private IContentModelFieldBiz fieldBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request,ModelMap model){
		model.addAttribute("searchType",BasicUtil.resToMap("com.mingsoft.mdiy.resources.search_type"));
		return view ("/mdiy/search/index");
	}
	
	/**
	 * 查询自定义搜索表列表
	 * @param search 自定义搜索表实体
	 * <i>search参数包含字段信息参考：</i><br/>
	 * searchId 自增长ID<br/>
	 * searchName 搜索名称<br/>
	 * searchTemplets 搜索结果模版<br/>
	 * searchWebsiteid 搜索管理的应用id<br/>
	 * searchType 搜索类型<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * searchId: 自增长ID<br/>
	 * searchName: 搜索名称<br/>
	 * searchTemplets: 搜索结果模版<br/>
	 * searchWebsiteid: 搜索管理的应用id<br/>
	 * searchType: 搜索类型<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute SearchEntity search,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		if(search == null){
			search = new SearchEntity();
		}
		search.setAppId(BasicUtil.getAppId());
		BasicUtil.startPage();
		List searchList = searchBiz.query(search);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(searchList,(int)BasicUtil.endPage(searchList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面search_form
	 */
	@RequestMapping("/form")
	public void form(@ModelAttribute SearchEntity search,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(search.getSearchId() <= 0){
			this.outJson(response, false);
			return;
		}
		SearchEntity searchEntity = (SearchEntity)searchBiz.getEntity(search);
		this.outJson(response, searchEntity);
	}
	
	/**
	 * 获取自定义搜索表
	 * @param search 自定义搜索表实体
	 * <i>search参数包含字段信息参考：</i><br/>
	 * searchId 自增长ID<br/>
	 * searchName 搜索名称<br/>
	 * searchTemplets 搜索结果模版<br/>
	 * searchWebsiteid 搜索管理的应用id<br/>
	 * searchType 搜索类型<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * searchId: 自增长ID<br/>
	 * searchName: 搜索名称<br/>
	 * searchTemplets: 搜索结果模版<br/>
	 * searchWebsiteid: 搜索管理的应用id<br/>
	 * searchType: 搜索类型<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute SearchEntity search,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(search.getSearchId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("search.id")));
			return;
		}
		SearchEntity _search = (SearchEntity)searchBiz.getEntity(search.getSearchId());
		this.outJson(response, _search);
	}
	
	/**
	 * 保存自定义搜索表实体
	 * @param search 自定义搜索表实体
	 * <i>search参数包含字段信息参考：</i><br/>
	 * searchId 自增长ID<br/>
	 * searchName 搜索名称<br/>
	 * searchTemplets 搜索结果模版<br/>
	 * searchWebsiteid 搜索管理的应用id<br/>
	 * searchType 搜索类型<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * searchId: 自增长ID<br/>
	 * searchName: 搜索名称<br/>
	 * searchTemplets: 搜索结果模版<br/>
	 * searchWebsiteid: 搜索管理的应用id<br/>
	 * searchType: 搜索类型<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	@RequiresPermissions("mdiy:search:save")
	public void save(@ModelAttribute SearchEntity search, HttpServletResponse response, HttpServletRequest request) {
		//验证搜索类型的值是否合法			
		if(StringUtil.isBlank(search.getSearchType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("search.type")));
			return;			
		}
		if(!StringUtil.checkLength(search.getSearchType()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("search.type"), "1", "255"));
			return;			
		}
		//验证搜索名称的值是否合法
		if(StringUtil.isBlank(search.getSearchName())){
			this.outJson(response, null, false, getResString("err.empty", this.getResString("search.name")));
			return;			
		}
		if(!StringUtil.checkLength(search.getSearchName()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("search.name"), "1", "255"));
			return;			
		}
		search.setAppId(BasicUtil.getAppId());
		searchBiz.saveEntity(search);
		this.outJson(response, JSONObject.toJSONString(search));
	}
	
	/**
	 * @param search 自定义搜索表实体
	 * <i>search参数包含字段信息参考：</i><br/>
	 * searchId:多个searchId直接用逗号隔开,例如searchId=1,2,3,4
	 * 批量删除自定义搜索表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("mdiy:search:del")
	public void delete(@RequestBody List<SearchEntity> searchs,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[searchs.size()];
		for(int i = 0;i<searchs.size();i++){
			ids[i] = searchs.get(i).getSearchId();
		}
		searchBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新自定义搜索表信息自定义搜索表
	 * @param search 自定义搜索表实体
	 * <i>search参数包含字段信息参考：</i><br/>
	 * searchId 自增长ID<br/>
	 * searchName 搜索名称<br/>
	 * searchTemplets 搜索结果模版<br/>
	 * searchWebsiteid 搜索管理的应用id<br/>
	 * searchType 搜索类型<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * searchId: 自增长ID<br/>
	 * searchName: 搜索名称<br/>
	 * searchTemplets: 搜索结果模版<br/>
	 * searchWebsiteid: 搜索管理的应用id<br/>
	 * searchType: 搜索类型<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	 
	@RequiresPermissions("mdiy:search:update")
	public void update(@ModelAttribute SearchEntity search, HttpServletResponse response,
			HttpServletRequest request) {
		//验证搜索类型的值是否合法			
		if(StringUtil.isBlank(search.getSearchType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("search.type")));
			return;			
		}
		if(!StringUtil.checkLength(search.getSearchType()+"", 1, 255)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("search.type"), "1", "255"));
			return;			
		}
		searchBiz.updateEntity(search);
		this.outJson(response, JSONObject.toJSONString(search));
	}
		
	/**
	 * 查询栏目自定义的字段名
	 * @param columnId 栏目ID
	 * @param model
	 * @param request 请求
	 */
	@RequestMapping("/{columnId}/queryFieldName")
	@ResponseBody
	public Map queryFieldName(@PathVariable int columnId, HttpServletRequest request){
		Map model = new HashMap();
		// 获取栏目信息
		ColumnEntity column = (ColumnEntity) columnBiz.getEntity(columnId);
		if(column!=null){
			//获取表单类型的id
			int fieldCmid = column.getColumnContentModelId();
			// 根据表单类型id查找出所有的字段信息
			List<BaseEntity> listField = fieldBiz.queryListByCmid(fieldCmid);
			model.put("listField",listField);
		}
		return model;
	}
	
	/**
	 * 生成搜索表单的html样式
	 * 
	 * @param model
	 * @param request
	 *            请求
	 * @return 返回html样式
	 */
	@RequestMapping("/generateSreachFormHtml")
	public String generateSreachFormHtml(ModelMap model, HttpServletRequest request) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		int searchId = 0;
		if (!StringUtil.isBlank(request.getParameter("searchId"))) {
			searchId = Integer.valueOf(request.getParameter("searchId"));
		}

		// 获取页面勾选的字段信息
		Map<String, String[]> field = new HashMap<String, String[]>();
		field = request.getParameterMap();
		int basicCategoryId = 0;
		int cmId = 0;
		Map<String, String> basicField = getMapByProperties(com.mingsoft.mdiy.constant.Const.BASIC_FIELD);
		Map<String, String> basicAttribute = getMapByProperties(com.mingsoft.mdiy.constant.Const.BASIC_ATTRIBUTE);
		List<Map<String, String>> listFieldName = new ArrayList<Map<String, String>>();
		for (Entry<String, String[]> entry : field.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue()[0];
			if (key.equals("columnId") && !StringUtil.isBlank(value) && !key.equals("searchId")) {
				basicCategoryId = Integer.valueOf(value);
			}
			if (!key.equals("columnId") && !key.equals("searchId")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", key);
				map.put("type", value);
				// 若为文章字段则直接取配置文件；若为自定义字段，则取数据库值
				if (!StringUtil.isBlank(basicField.get(key))) {
					map.put("ch", basicField.get(key));
				} else {
					// 若栏目ID不为0，则选择了栏目
					if (basicCategoryId != 0) {
						ColumnEntity column = (ColumnEntity) columnBiz.getEntity(Integer.valueOf(basicCategoryId));
						// 获取表单类型的id
						cmId = column.getColumnContentModelId();
					}
					ContentModelFieldEntity fieldEntity = (ContentModelFieldEntity) fieldBiz.getEntityByCmId(cmId, key);
					if (!StringUtil.isBlank(fieldEntity)) {
						String fieldTipsName = fieldEntity.getFieldTipsName();
						map.put("ch", fieldTipsName);
					}
				}
				if (key.equals("article_type")) {
					map.put("default", basicAttribute.toString());
				} else {
					map.put("default", key.toString());
				}
				listFieldName.add(map);
			}
		}
		model.addAttribute("searchId", searchId);
		model.addAttribute("websiteId", managerSession.getBasicId());
		model.addAttribute("listFieldName", listFieldName);
		model.addAttribute("basicCategoryId", basicCategoryId);
		return view("/mdiy/search/search_field");
	}
	
	/**
	 * 跳转至创建搜索页面
	 * @param model
	 * @param request 请求
	 * @return 返回页面
	 */
	@RequestMapping("/{searchId}/searchCode")
	public String searchCode(@PathVariable int searchId,ModelMap model ,HttpServletRequest request){
 		List<ColumnEntity> columnList = columnBiz.queryColumnListByWebsiteId(this.getAppId(request));
 		SearchEntity searchEntity = new SearchEntity();
 		searchEntity.setSearchId(searchId);
 		SearchEntity search = (SearchEntity) searchBiz.getEntity(searchEntity);
 		model.addAttribute("columnList", JSONArray.toJSONString(columnList));
 		model.addAttribute("searchId",searchId);
 		model.addAttribute("searchType",search.getSearchType());
		return view("/mdiy/search/search_code");
	}
}