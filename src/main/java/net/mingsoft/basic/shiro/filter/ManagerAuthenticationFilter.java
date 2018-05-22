package net.mingsoft.basic.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.WebUtils;

import com.mingsoft.basic.constant.e.SessionConstEnum;
import com.mingsoft.basic.entity.ManagerSessionEntity;

import net.mingsoft.base.util.BaseUtil;

/**
 * ms-basic 后台管理过滤器
 * 
 * @author MY开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2017年9月6日<br/>
 *          历史修订：<br/>
 */
public class ManagerAuthenticationFilter extends org.apache.shiro.web.filter.authc.AuthenticationFilter {

	/**
	 * 检查是否有权限访问 return false中断
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		HttpServletRequest hsq = (HttpServletRequest)request;
		ManagerSessionEntity mse = (ManagerSessionEntity)hsq.getSession().getAttribute(SessionConstEnum.MANAGER_SESSION.toString());
		if(mse != null) {
			return true;
		}
		return false;
	}
	/**
	 * 验证管理员是否登录
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Object mse =  BaseUtil.getSession(SessionConstEnum.MANAGER_SESSION);
		if(mse == null) {
			HttpServletRequest hsr = (HttpServletRequest)request;
			WebUtils.toHttp(response).sendRedirect(hsr.getContextPath()+super.getLoginUrl());
		}
		return false;
	}

}
