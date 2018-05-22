/**
The MIT License (MIT) * Copyright (c) 2016 MY科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.basic.action.web;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mingsoft.basic.action.BaseAction;
import com.mingsoft.basic.biz.IAppBiz;
import com.mingsoft.basic.biz.IManagerBiz;
import com.mingsoft.basic.biz.IRoleBiz;
import com.mingsoft.basic.biz.ISystemSkinBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.ModelCode;
import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.ManagerEntity;
import com.mingsoft.basic.entity.ManagerSessionEntity;
import com.mingsoft.basic.entity.RoleEntity;
import com.mingsoft.basic.entity.SystemSkinEntity;
import com.mingsoft.util.FileUtil;
import com.mingsoft.util.StringUtil;

import net.mingsoft.base.util.BaseUtil;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;

/**
 * 登录的基础应用层
 * 
 * @author 王天培
 * @version 版本号：100-000-000<br/>
 *          创建日期：2015-1-10<br/>
 *          历史修订：<br/>
 */
@Controller
@RequestMapping("/${managerPath}")
public class LoginAction extends BaseAction {
	
	@Value("${managerPath}")
	private String managerPath;
	/**
	 * 管理员业务层
	 */
	@Autowired
	private IManagerBiz managerBiz;

	/**
	 * 角色业务request层
	 */
	@Autowired
	private IRoleBiz roleBiz;

	/**
	 * 站点业务层
	 */
	@Autowired
	private IAppBiz appBiz;

	/**
	 * 系统皮肤业务处理层
	 */
	@Autowired
	private ISystemSkinBiz systemSkinBiz;

	/**
	 * 加载管理员登录界面
	 * 
	 * @param request
	 *            请求对象
	 * @return 管理员登录界面地址
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		if (BasicUtil.getSession(SessionConstEnum.MANAGER_SESSION)!=null) {
			return "redirect:"+managerPath+"/index.do";
		}
		// 根据请求地址来显示标题
		AppEntity app = BasicUtil.getApp();
		// 判断应用实体是否存在
		if (app != null) {
			// 检测应用是否有自定义界面b
			if (!StringUtil.isBlank(app.getAppLoginPage())) {
				LOG.debug("跳转自定义登录界面");
				return "redirect:/" + app.getAppLoginPage();
			}
			
		} else {
			File file = new File(this.getRealPath(request, "WEB-INF/ms.install"));
			//存在安装文件
			if (file.exists()) {
				String defaultId = FileUtil.readFile(this.getRealPath(request, "WEB-INF/ms.install")).trim();
				if (!StringUtil.isBlank(defaultId)) {
					app = (AppEntity) appBiz.getEntity(Integer.parseInt(defaultId));
					app.setAppUrl(this.getUrl(request));
					appBiz.updateEntity(app);
					FileUtil.writeFile(defaultId, this.getRealPath(request, "WEB-INF/ms.install.bak"), com.mingsoft.base.constant.Const.UTF8);
					file.delete();
				}
			} 

		}
		request.setAttribute("app", app);
		return view("/login");
	}

	/**
	 * 验证登录
	 * 
	 * @param manager
	 *            管理员实体
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	public void checkLogin(@ModelAttribute ManagerEntity manager, HttpServletRequest request,
			HttpServletResponse response) {
		AppEntity urlWebsite = null;
		urlWebsite = appBiz.getByUrl(this.getDomain(request)); // 根据url地址获取站点信息，主要是区分管理员对那些网站有权限
		if (urlWebsite == null) {
			this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err.not.exist",this.getResString("app"),"!请尝试去文件 WEB-INF/ms.install.bak 后缀bak"));
			return;
		}
		// 根据账号获取当前管理员信息
		ManagerEntity newManager = new ManagerEntity();
		newManager.setManagerName(manager.getManagerName());
		ManagerEntity _manager = (ManagerEntity) managerBiz.getEntity(newManager);
		if (_manager == null || StringUtil.isBlank(manager.getManagerName())) {
			// 系统不存在此用户
			this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err.nameEmpty"));
		} else {
			// 判断当前用户输入的密码是否正确
			if (StringUtil.Md5(manager.getManagerPassword()).equals(_manager.getManagerPassword())) {
				SystemSkinEntity systemSkin = (SystemSkinEntity)systemSkinBiz.getEntity(_manager.getManagerSystemSkinId());
				// 创建管理员session对象
				ManagerSessionEntity managerSession = new ManagerSessionEntity();
				AppEntity website = new AppEntity();
				// 获取管理员所在的角色
				RoleEntity role = (RoleEntity) roleBiz.getEntity(_manager.getManagerRoleID());
				website = (AppEntity) appBiz.getByManagerId(role.getRoleManagerId());
				// 判断当前登录管理员是否为该网站的系统管理员，如果是，如果不是则判断是否为超级管理员
				if ((website != null && urlWebsite != null && urlWebsite.getAppId() == website.getAppId()
						&& _manager.getManagerRoleID() > Const.DEFAULT_SYSTEM_MANGER_ROLE_ID) 
						||(role.getAppId()==this.getAppId(request))) {
					if(website==null){
						website = BasicUtil.getApp();
					}
					List childManagerList = managerBiz.queryAllChildManager(managerSession.getManagerId());
					managerSession.setBasicId(website.getAppId());    
					managerSession.setManagerParentID(role.getRoleManagerId());
					managerSession.setManagerChildIDs(childManagerList);
					managerSession.setStyle(website.getAppStyle());
					// 压入管理员seesion
					BaseUtil.setSession(SessionConstEnum.MANAGER_SESSION, managerSession);
				} else {
					if (!(_manager.getManagerRoleID() == Const.DEFAULT_SYSTEM_MANGER_ROLE_ID)) {
						LOG.debug("roleId: "+_manager.getManagerRoleID());
						this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err.not.exist",this.getResString("manager")));
					} else {
						BaseUtil.setSession(SessionConstEnum.MANAGER_SESSION, managerSession);
					}
				} 
				BeanUtils.copyProperties(_manager, managerSession);
				if (systemSkin != null) {
					managerSession.setSystemSkin(systemSkin);
				}

				Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken upt = new UsernamePasswordToken(managerSession.getManagerName(),
						managerSession.getManagerPassword());
				subject.login(upt);
				this.outJson(response, ModelCode.ADMIN_LOGIN, true, null);
			} else {
				// 密码错误
				this.outJson(response, ModelCode.ADMIN_LOGIN, false, this.getResString("err.password"));
			}
		}
	}

}