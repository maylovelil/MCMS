package com.mingsoft.basic.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.IRoleBiz;
import com.mingsoft.basic.biz.IRoleModelBiz;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.basic.entity.RoleEntity;
import com.mingsoft.basic.entity.RoleModelEntity;

import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.bean.ListBean;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 角色管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：1.0<br/>
 * 创建日期：2017-8-24 23:40:55<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/basic/role")
public class RoleAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入角色业务层
	 */	
	@Autowired
	private IRoleBiz roleBiz;
	/**
	 * 模块业务层
	 */
	@Autowired
	private IModelBiz modelBiz;
	/**
	 * 角色模块关联业务层
	 */
	@Autowired
	private IRoleModelBiz roleModelBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/basic/role/index");
	}
	
	/**
	 * 查询角色列表
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * appId 角色APPID<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * appId: 角色APPID<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute RoleEntity role,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		role.setRoleManagerId(managerSession.getManagerId());
		role.setAppId(BasicUtil.getAppId());
		BasicUtil.startPage();
		List roleList = roleBiz.query(role);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(roleList,(int)BasicUtil.endPage(roleList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	@RequestMapping("/{roleId}/queryByRole")
	@ResponseBody
	public void queryByRole(@PathVariable int roleId, HttpServletResponse response){
		List models = modelBiz.queryModelByRoleId(roleId);
		this.outJson(response, JSONObject.toJSONString(models));
	}
	/**
	 * 返回编辑界面role_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute RoleEntity role,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(role.getRoleId() > 0){
			BaseEntity roleEntity = roleBiz.getEntity(role.getRoleId());			
			model.addAttribute("roleEntity",roleEntity);
		}
		return view ("/basic/role/form");
	}
	
	/**
	 * 获取角色
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * appId: 角色APPID<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute RoleEntity role,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(role.getRoleId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("role.id")));
			return;
		}
		RoleEntity _role = (RoleEntity)roleBiz.getEntity(role.getRoleId());
		this.outJson(response, _role);
	}
	
	/**
	 * 保存角色实体
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId 角色ID，自增长<br/>
	 * roleName 角色名<br/>
	 * roleManagerid 角色管理员编号<br/>
	 * appId 角色APPID<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * roleId: 角色ID，自增长<br/>
	 * roleName: 角色名<br/>
	 * roleManagerid: 角色管理员编号<br/>
	 * appId: 角色APPID<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/saveOrUpdateRole")
	@ResponseBody
	@RequiresPermissions("role:save")
	public void saveOrUpdateRole(@ModelAttribute RoleEntity role,@RequestParam(value="ids[]",required=false) List<Integer> ids, HttpServletResponse response, HttpServletRequest request) {
		//组织角色属性，并对角色进行保存
		RoleEntity _role = new RoleEntity();
		_role.setRoleName(role.getRoleName());
		//给角色添加APPID
		role.setAppId(BasicUtil.getAppId());
		//获取管理员id
		ManagerSessionEntity managerSession = getManagerBySession(request);
		role.setRoleManagerId(managerSession.getManagerId());
		if(StringUtils.isEmpty(role.getRoleName())){
			this.outJson(response, ModelCode.ROLE, false, getResString("err.empty", this.getResString("rolrName")));	
			return;
		}
		//通过角色id判断是保存还是修改
		if(role.getRoleId()>0){
			//若为更新角色，数据库中存在该角色名称且当前名称不为更改前的名称，则属于重名
			if(roleBiz.getEntity(_role) != null && !role.getRoleName().equals(BasicUtil.getString("oldRoleName"))){
				this.outJson(response, ModelCode.ROLE, false, getResString("roleName.exist"));	
				return;
			}
			roleBiz.updateEntity(role);
		}else{
			//判断角色名是否重复
			if(roleBiz.getEntity(_role) != null){
				this.outJson(response, ModelCode.ROLE, false, getResString("roleName.exist"));	
				return;
			}
			//获取管理员id
			roleBiz.saveEntity(role);
		}
		//开始保存相应的关联数据。组织角色模块的列表。
		List<RoleModelEntity> roleModelList = new ArrayList<>();
		if(ids != null){
			for(Integer id : ids){
				RoleModelEntity roleModel = new RoleModelEntity();
				roleModel.setRoleId(role.getRoleId());
				roleModel.setModelId(id);
				roleModelList.add(roleModel);
			}
			//先删除当前的角色关联菜单，然后重新添加。
			roleModelBiz.deleteEntity(role.getRoleId());
			roleModelBiz.saveEntity(roleModelList);
		}else{
			roleModelBiz.deleteEntity(role.getRoleId());
		}
		
		this.outJson(response, JSONObject.toJSONString(role));
	}
	
	/**
	 * @param role 角色实体
	 * <i>role参数包含字段信息参考：</i><br/>
	 * roleId:多个roleId直接用逗号隔开,例如roleId=1,2,3,4
	 * 批量删除角色
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("role:del")
	public void delete(@RequestBody List<RoleEntity> roles,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[roles.size()];
		ManagerSessionEntity managerSession = this.getManagerBySession(request);
		int currentRoleId = managerSession.getManagerRoleID();
		for(int i = 0;i<roles.size();i++){
			if(currentRoleId == roles.get(i).getRoleId()){
				//当前角色不能删除
				continue ;
			}
			ids[i] = roles.get(i).getRoleId();
		}
		roleBiz.delete(ids);		
		this.outJson(response, true);
	}
}