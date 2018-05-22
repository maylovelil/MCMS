package com.mingsoft.basic.action;

import java.util.Date;
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
import com.mingsoft.basic.biz.IManagerBiz;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;

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
 * 管理员管理控制层
 * @author 伍晶晶
 * @version 
 * 版本号：1.0<br/>
 * 创建日期：2017-8-24 23:40:55<br/>
 * 历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}/basic/manager")
public class ManagerAction extends com.mingsoft.basic.action.BaseAction{
	
	/**
	 * 注入管理员业务层
	 */	
	@Autowired
	private IManagerBiz managerBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/basic/manager/index");
	}
	
	/**
	 * 查询管理员列表
	 * @param manager 管理员实体
	 * <i>manager参数包含字段信息参考：</i><br/>
	 * managerId 管理员ID(主键)<br/>
	 * managerName 管理员用户名<br/>
	 * managerNickname 管理员昵称<br/>
	 * managerPassword 管理员密码<br/>
	 * managerRoleid 角色编号<br/>
	 * managerPeopleid 用户编号即商家编号<br/>
	 * managerTime 管理员创建时间<br/>
	 * managerSystemSkinId 管理员主界面样式<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * managerId: 管理员ID(主键)<br/>
	 * managerName: 管理员用户名<br/>
	 * managerNickname: 管理员昵称<br/>
	 * managerPassword: 管理员密码<br/>
	 * managerRoleid: 角色编号<br/>
	 * managerPeopleid: 用户编号即商家编号<br/>
	 * managerTime: 管理员创建时间<br/>
	 * managerSystemSkinId: 管理员主界面样式<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute ManagerEntity manager,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		BasicUtil.startPage();
		List managerList = managerBiz.queryAllChildManager(managerSession.getManagerId());
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(managerList,(int)BasicUtil.endPage(managerList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	/**
	 * 查询管理员列表,去掉当前管理员id，确保不能删除和修改自己
	 * @param manager 管理员实体
	 * <i>manager参数包含字段信息参考：</i><br/>
	 * managerId 管理员ID(主键)<br/>
	 * managerName 管理员用户名<br/>
	 * managerNickname 管理员昵称<br/>
	 * managerPassword 管理员密码<br/>
	 * managerRoleid 角色编号<br/>
	 * managerPeopleid 用户编号即商家编号<br/>
	 * managerTime 管理员创建时间<br/>
	 * managerSystemSkinId 管理员主界面样式<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * managerId: 管理员ID(主键)<br/>
	 * managerName: 管理员用户名<br/>
	 * managerNickname: 管理员昵称<br/>
	 * managerPassword: 管理员密码<br/>
	 * managerRoleid: 角色编号<br/>
	 * managerPeopleid: 用户编号即商家编号<br/>
	 * managerTime: 管理员创建时间<br/>
	 * managerSystemSkinId: 管理员主界面样式<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/query")
	@ResponseBody
	public void query(@ModelAttribute ManagerEntity manager,HttpServletResponse response, HttpServletRequest request,ModelMap model) {
		ManagerSessionEntity managerSession = getManagerBySession(request);
		BasicUtil.startPage();
		List<ManagerEntity> managerList = managerBiz.queryAllChildManager(managerSession.getManagerId());
		for(ManagerEntity _manager : managerList){
			if(_manager.getManagerId() == managerSession.getManagerId()){
				_manager.setManagerId(0);
			}
		}
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(managerList,(int)BasicUtil.endPage(managerList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	/**
	 * 获取管理员
	 * @param manager 管理员实体
	 * <i>manager参数包含字段信息参考：</i><br/>
	 * managerId 管理员ID(主键)<br/>
	 * managerName 管理员用户名<br/>
	 * managerNickname 管理员昵称<br/>
	 * managerPassword 管理员密码<br/>
	 * managerRoleid 角色编号<br/>
	 * managerPeopleid 用户编号即商家编号<br/>
	 * managerTime 管理员创建时间<br/>
	 * managerSystemSkinId 管理员主界面样式<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * managerId: 管理员ID(主键)<br/>
	 * managerName: 管理员用户名<br/>
	 * managerNickname: 管理员昵称<br/>
	 * managerPassword: 管理员密码<br/>
	 * managerRoleid: 角色编号<br/>
	 * managerPeopleid: 用户编号即商家编号<br/>
	 * managerTime: 管理员创建时间<br/>
	 * managerSystemSkinId: 管理员主界面样式<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute ManagerEntity manager,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(manager.getManagerId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("manager.id")));
			return;
		}
		ManagerEntity managerEntity = (ManagerEntity)managerBiz.getEntity(manager.getManagerId());
		managerEntity.setManagerPassword("");
		this.outJson(response, managerEntity);
	}
	
	/**
	 * 保存管理员实体
	 * @param manager 管理员实体
	 * <i>manager参数包含字段信息参考：</i><br/>
	 * managerId 管理员ID(主键)<br/>
	 * managerName 管理员用户名<br/>
	 * managerNickname 管理员昵称<br/>
	 * managerPassword 管理员密码<br/>
	 * managerRoleid 角色编号<br/>
	 * managerPeopleid 用户编号即商家编号<br/>
	 * managerTime 管理员创建时间<br/>
	 * managerSystemSkinId 管理员主界面样式<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * managerId: 管理员ID(主键)<br/>
	 * managerName: 管理员用户名<br/>
	 * managerNickname: 管理员昵称<br/>
	 * managerPassword: 管理员密码<br/>
	 * managerRoleid: 角色编号<br/>
	 * managerPeopleid: 用户编号即商家编号<br/>
	 * managerTime: 管理员创建时间<br/>
	 * managerSystemSkinId: 管理员主界面样式<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	@RequiresPermissions("manager:save")
	public void save(@ModelAttribute ManagerEntity manager, HttpServletResponse response, HttpServletRequest request) {
		ManagerEntity newManager = new ManagerEntity();
		newManager.setManagerName(manager.getManagerName());
		//用户名是否存在
		if(managerBiz.getEntity(newManager) != null){
			this.outJson(response, null,false,getResString("err.exist", this.getResString("manager.name")));
			return;
		}
		//验证管理员用户名的值是否合法			
		if(StringUtil.isBlank(manager.getManagerName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("manager.name")));
			return;			
		}
		if(!StringUtil.checkLength(manager.getManagerName()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("manager.name"), "1", "15"));
			return;			
		}
		//验证管理员昵称的值是否合法			
		if(StringUtil.isBlank(manager.getManagerNickName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("manager.nickname")));
			return;			
		}
		if(!StringUtil.checkLength(manager.getManagerNickName()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("manager.nickname"), "1", "15"));
			return;			
		}
		//验证管理员密码的值是否合法			
		if(StringUtil.isBlank(manager.getManagerPassword())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("manager.password")));
			return;			
		}
		if(!StringUtil.checkLength(manager.getManagerPassword()+"", 1, 45)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("manager.password"), "1", "45"));
			return;			
		}
		manager.setManagerPassword(StringUtil.Md5(manager.getManagerPassword()));
		manager.setManagerTime(new Date());
		managerBiz.saveEntity(manager);
		this.outJson(response, JSONObject.toJSONString(manager));
	}
	
	/**
	 * @param manager 管理员实体
	 * <i>manager参数包含字段信息参考：</i><br/>
	 * managerId:多个managerId直接用逗号隔开,例如managerId=1,2,3,4
	 * 批量删除管理员
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("manager:del")
	public void delete(@RequestBody List<ManagerEntity> managers,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[managers.size()];
		for(int i = 0;i<managers.size();i++){
			ids[i] = managers.get(i).getManagerId();
		}
		managerBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新管理员信息管理员
	 * @param manager 管理员实体
	 * <i>manager参数包含字段信息参考：</i><br/>
	 * managerId 管理员ID(主键)<br/>
	 * managerName 管理员用户名<br/>
	 * managerNickname 管理员昵称<br/>
	 * managerPassword 管理员密码<br/>
	 * managerRoleid 角色编号<br/>
	 * managerPeopleid 用户编号即商家编号<br/>
	 * managerTime 管理员创建时间<br/>
	 * managerSystemSkinId 管理员主界面样式<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * managerId: 管理员ID(主键)<br/>
	 * managerName: 管理员用户名<br/>
	 * managerNickname: 管理员昵称<br/>
	 * managerPassword: 管理员密码<br/>
	 * managerRoleid: 角色编号<br/>
	 * managerPeopleid: 用户编号即商家编号<br/>
	 * managerTime: 管理员创建时间<br/>
	 * managerSystemSkinId: 管理员主界面样式<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody
	@RequiresPermissions("manager:update")
	public void update(@ModelAttribute ManagerEntity manager, HttpServletResponse response,
			HttpServletRequest request) {
		//验证管理员用户名的值是否合法			
		if(StringUtil.isBlank(manager.getManagerName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("manager.name")));
			return;			
		}
		if(!StringUtil.checkLength(manager.getManagerName()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("manager.name"), "1", "15"));
			return;			
		}
		//验证管理员昵称的值是否合法			
		if(StringUtil.isBlank(manager.getManagerNickName())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("manager.nickname")));
			return;			
		}
		if(!StringUtil.checkLength(manager.getManagerNickName()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("manager.nickname"), "1", "15"));
			return;			
		}
		//验证管理员密码的值是否合法			
		if(StringUtil.isBlank(manager.getManagerPassword())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("manager.password")));
			return;			
		}
		if(!StringUtil.checkLength(manager.getManagerPassword()+"", 1, 45)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("manager.password"), "1", "45"));
			return;			
		}
		manager.setManagerPassword(StringUtil.Md5(manager.getManagerPassword()));
		managerBiz.updateEntity(manager);
		this.outJson(response, JSONObject.toJSONString(manager));
	}
		
}