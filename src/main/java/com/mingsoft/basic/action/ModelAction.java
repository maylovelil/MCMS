package com.mingsoft.basic.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.IManagerBiz;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.IRoleModelBiz;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.basic.entity.RoleModelEntity;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;

/**
 * 模块控制层
 * @author 史爱华
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014-6-29<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/model")
public class ModelAction extends BaseAction {

	/**
	 * 注入模块业务层
	 */
	@Autowired
	private IModelBiz modelBiz;
	
	@Autowired
	private IManagerBiz managerBiz;
	/**
	 * 角色模块关联业务层
	 */
	@Autowired
	private IRoleModelBiz roleModelBiz;

	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request,ModelMap mode){
		ManagerSessionEntity managerSession = this.getManagerBySession(request);
		int currentRoleId = managerSession.getManagerRoleID();
		List<BaseEntity> parentModelList = modelBiz.queryModelByRoleId(currentRoleId);
		mode.addAttribute("parentModelList", JSONArray.toJSONString(parentModelList));
		return view ("/model/index");
	}

	
	/**
	 * 查询模块表列表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		ManagerSessionEntity managerSession = this.getManagerBySession(request);
		int currentRoleId = managerSession.getManagerRoleID();
		List<BaseEntity> modelList = modelBiz.queryModelByRoleId(currentRoleId);
		EUListBean _list = new EUListBean(modelList, modelList.size());
		this.outJson(response,net.mingsoft.base.util.JSONArray.toJSONString(_list));
	}
	
	/**
	 * 获取模块表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(modelEntity.getModelId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("model.id")));
			return;
		}
		//根据父模块id查寻模块
		ModelEntity _model = (ModelEntity)modelBiz.getEntity(modelEntity.getModelId());
		if(_model != null){
			Map<String, ModelEntity> mode = new HashMap<String, ModelEntity>();
			ModelEntity parentModel = (ModelEntity) modelBiz.getEntity(_model.getModelModelId());
			mode.put("parentModel", parentModel);
			mode.put("model", _model);
			this.outJson(response, JSONObject.toJSONString(mode));
			return;
		}
		this.outJson(response, _model);
	}
	
	/**
	 * 保存模块表实体
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	@RequiresPermissions("model:save")
	public void save(@ModelAttribute ModelEntity model, HttpServletResponse response, HttpServletRequest request) {
		//模块标题验证
		if(StringUtil.isBlank(model.getModelTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("model.title")));
			return;			
		}
		if(!StringUtil.checkLength(model.getModelTitle()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("model.title"), "1", "10"));
			return;			
		}
		//模块编码验证
		if(StringUtil.isBlank(model.getModelCode())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("model.code")));
			return;			
		}
		if(!StringUtil.checkLength(model.getModelCode()+"", 7, 9)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("model.code"), "7", "9"));
			return;			
		}
		//获取管理员id并赋值给模块的id
		model.setModelId(getManagerId(request));
		// 获取模块保存时间
		model.setModelDatetime(new Timestamp(System.currentTimeMillis()));
		//判断图标是否为空，不为空去掉,图标地址中含有的“|”
		//空值判断
		if(!StringUtil.isBlank(model.getModelIcon())) {
			model.setModelIcon( model.getModelIcon().replace("|", ""));
		}
		//添加父级id集合
		ModelEntity parent = (ModelEntity) modelBiz.getEntity(model.getModelModelId());
		String parentIds="";
		if(parent != null){
			if(parent.getModelParentIds() != null){
				parentIds = parent.getModelParentIds()+","+model.getModelModelId();
			}else{
				parentIds = model.getModelModelId()+"";
			}
		}
		model.setModelParentIds(parentIds);
		modelBiz.saveEntity(model);
		//保存成功后给当前管理就就加上对应的权限
		if(model.getModelId() > 0){
			ManagerSessionEntity managerSession = this.getManagerBySession(request);
			List<RoleModelEntity> roleModels = new ArrayList<>(); 
			RoleModelEntity rolemodel = new RoleModelEntity();
			rolemodel.setModelId(model.getModelId());
			rolemodel.setRoleId(managerSession.getManagerRoleID());
			roleModels.add(rolemodel);
			roleModelBiz.saveEntity(roleModels);
		}
		//返回模块id到页面
		this.outJson(response, ModelCode.ROLE, true,String.valueOf(model.getModelId()));
	}
	
	/**
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId:多个modelId直接用逗号隔开,例如modelId=1,2,3,4
	 * 批量删除模块表
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("model:del")
	public void delete(HttpServletResponse response, HttpServletRequest request) {
		int[] ids = BasicUtil.getInts("ids", ",");
		modelBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新模块表信息模块表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@RequiresPermissions("model:update")
	@ResponseBody	 
	public void update(@ModelAttribute ModelEntity model, HttpServletResponse response,
			HttpServletRequest request) {
		//模块标题验证
		if(StringUtil.isBlank(model.getModelTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("model.title")));
			return;			
		}
		if(!StringUtil.checkLength(model.getModelTitle()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("model.title"), "1", "10"));
			return;			
		}
		//模块编码验证
		if(StringUtil.isBlank(model.getModelCode())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("model.code")));
			return;			
		}
		if(!StringUtil.checkLength(model.getModelCode()+"", 7, 9)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("model.code"), "7", "9"));
			return;			
		}
		//判断图标是否为空，不为空去掉,图标地址中含有的“|”
		//空值判断
		if(!StringUtil.isBlank(model.getModelIcon())) {
			model.setModelIcon( model.getModelIcon().replace("|", ""));
		}
		//添加父级id集合
		ModelEntity parent = (ModelEntity) modelBiz.getEntity(model.getModelModelId());
		String parentIds="";
		if(parent != null){
			if(parent.getModelParentIds() != null){
				parentIds = parent.getModelParentIds()+","+model.getModelModelId();
			}else{
				parentIds = model.getModelModelId()+"";
			}
		}
		model.setModelParentIds(parentIds);
		modelBiz.updateEntity(model);		
		this.outJson(response, ModelCode.ROLE, true,String.valueOf(model.getModelId()));
	}
	
	/**
	 * 根据管理员ID查询模块集合
	 * @param managerId 管理员id
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	@RequestMapping("/{managerId}/queryModelByRoleId")
	public void queryModelByRoleId(@PathVariable int managerId,HttpServletRequest request, HttpServletResponse response) {
		ManagerEntity manager =(ManagerEntity) managerBiz.getEntity(managerId);
		if(manager==null){
			return;
		}
		List<BaseEntity> modelList = new ArrayList<BaseEntity>();
		modelList = modelBiz.queryModelByRoleId(manager.getManagerRoleID());
		this.outJson(response, null,true, JSONObject.toJSONString(modelList));

	}
	
	/**
	 * 查询模块表列表
	 * @param model 模块表实体
	 * <i>model参数包含字段信息参考：</i><br/>
	 * modelId 模块自增长id<br/>
	 * modelTitle 模块标题<br/>
	 * modelCode 模块编码<br/>
	 * modelModelid 模块的父模块id<br/>
	 * modelUrl 模块连接地址<br/>
	 * modelDatetime <br/>
	 * modelIcon 模块图标<br/>
	 * modelModelmanagerid 模块关联的关联员id<br/>
	 * modelSort 模块的排序<br/>
	 * modelIsmenu 模块是否是菜单<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * modelId: 模块自增长id<br/>
	 * modelTitle: 模块标题<br/>
	 * modelCode: 模块编码<br/>
	 * modelModelid: 模块的父模块id<br/>
	 * modelUrl: 模块连接地址<br/>
	 * modelDatetime: <br/>
	 * modelIcon: 模块图标<br/>
	 * modelModelmanagerid: 模块关联的关联员id<br/>
	 * modelSort: 模块的排序<br/>
	 * modelIsmenu: 模块是否是菜单<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/modelList")
	@ResponseBody
	public void modelList(@ModelAttribute ModelEntity modelEntity,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		int roleId = BasicUtil.getInt("roleId");
		ManagerSessionEntity managerSession = this.getManagerBySession(request);
		int currentRoleId = managerSession.getManagerRoleID();
		boolean updateFalg = true;
		//新增角色roleId为0，默认当前管理员的roleId
		if(roleId == 0){
			updateFalg = false;
			roleId = currentRoleId;
		}
		List<BaseEntity> modelList = modelBiz.queryModelByRoleId(currentRoleId);
		List<ModelEntity> _modelList = new ArrayList<>();
		List<RoleModelEntity> roleModelList = new ArrayList<>();
		if(roleId>0){
			roleModelList = roleModelBiz.queryByRoleId(roleId);
		}
		List<ModelEntity> childModelList = new ArrayList<>();
		//将菜单和功能区分开
		for(BaseEntity base : modelList){
			ModelEntity _model = (ModelEntity) base;
			if(_model.getModelIsMenu() == 1){
				_model.setModelChildList(new ArrayList<ModelEntity>());
				_modelList.add(_model);
			}else if(_model.getModelIsMenu() == 0){
				childModelList.add(_model);
			}
		}
		//菜单和功能一一匹配
		for(ModelEntity _modelEntity : _modelList){
			for(ModelEntity childModel : childModelList){
				if(childModel.getModelModelId() == _modelEntity.getModelId()){
					_modelEntity.getModelChildList().add(childModel);
					//选中状态
					for(RoleModelEntity roleModelEntity : roleModelList){
						if(roleModelEntity.getModelId() == childModel.getModelId() && updateFalg){
							childModel.setChick(1);
						}
					}
					
				}
			}
		} 
		EUListBean _list = new EUListBean(_modelList, _modelList.size());
		this.outJson(response,net.mingsoft.base.util.JSONArray.toJSONString(_list));
	}
}
