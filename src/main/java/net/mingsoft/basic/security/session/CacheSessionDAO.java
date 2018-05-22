package net.mingsoft.basic.security.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import net.mingsoft.basic.util.SpringUtil;

/**
 * session会话持久化
 * @author Administrator
 *
 */
@Deprecated
public class CacheSessionDAO extends EnterpriseCacheSessionDAO implements SessionDAO {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    public CacheSessionDAO() {
        super();
    }

    @Override
    protected Serializable doCreate(Session session) {
		HttpServletRequest request = SpringUtil.getRequest();
		if (request != null){
			String uri = request.getServletPath();
//			// 如果是静态文件，则不创建SESSION
//			if (SpringUtil.isStaticFile(uri)){
//		        return null;
//			}
		}
		super.doCreate(session);
		logger.debug("doCreate {} {}", session, request != null ? request.getRequestURI() : "");
    	return session.getId();
    }

    @Override
    protected void doDelete(Session session) {
    	if (session == null || session.getId() == null) {  
            return;
        }
    	
    	super.doDelete(session);
    	logger.debug("delete {} ", session.getId());
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
    	logger.debug("doReadSession {} ", sessionId);
		return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
    	logger.debug("doUpdate {} ", session.getId());

    	if (session == null || session.getId() == null) {  
            return;
        }
    	
    	HttpServletRequest request = SpringUtil.getRequest();
		if (request != null){
			String uri = request.getServletPath();
//			// 如果是静态文件，则不更新SESSION
//			if (SpringUtil.isStaticFile(uri)){
//				return;
//			}
//			// 如果是视图文件，则不更新SESSION
//			if (StringUtils.startsWith(uri, Global.getConfig("web.view.prefix"))
//					&& StringUtils.endsWith(uri, Global.getConfig("web.view.suffix"))){
//				return;
//			}
//			// 手动控制不更新SESSION
//			String updateSession = request.getParameter("updateSession");
//			if (Global.FALSE.equals(updateSession) || Global.NO.equals(updateSession)){
//				return;
//			}
		}
    	super.doUpdate(session);
    }
    
    @Override
	    public Session readSession(Serializable sessionId) throws UnknownSessionException {
    	
	    	try{
	    		Session s = null;
	    		HttpServletRequest request = SpringUtil.getRequest();
	    		if (request != null){
	    			String uri = request.getServletPath();
	    			//logger.debug("readSession " + uri);
	    			// 如果是静态文件，则不获取SESSION
	//    			if (SpringUtil.isStaticFile(uri)){
	//    				return null;
	//    			}
	    			s = (Session)request.getAttribute("session_"+sessionId);
	    		}
	    		if (s != null){
	    			return s;
	    		}
	
	    		Session session = super.readSession(sessionId);
	    		//logger.debug("readSession {} {}", sessionId, request != null ? request.getRequestURI() : "");
	    		
	    		if (request != null && session != null){
	    			request.setAttribute("session_"+sessionId, session);
	    		}
	    		
	    		return session;
	    	}catch (UnknownSessionException e) {
				return null;
			}
	    }

	/**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave) {
		logger.debug("getActiveSessions 获取活动会话");
		return getActiveSessions(includeLeave, null, null);
	}

    /**
	 * 获取活动会话
	 * @param includeLeave 是否包括离线
	 * @param principal 根据登录者对象获取活动会话
	 * @param filterSession 不为空，则过滤掉（不包含）这个会话。
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession) {
		logger.debug("getActiveSessions");
		// 如果包括离线，并无登录者条件。
		if (includeLeave && principal == null){
			return getActiveSessions();
		}
		Set<Session> sessions = Sets.newHashSet();
		for (Session session : getActiveSessions()){
			boolean isActiveSession = false;
			// 不包括离线并符合最后访问时间小于等于3分钟条件。
			
			if (includeLeave || com.mingsoft.util.DateUtil.diffMonth(session.getLastAccessTime(), new java.util.Date()) <= 3){
				isActiveSession = true;
			}
			// 符合登陆者条件。
			if (principal != null){
				PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if (principal.toString().equals(pc != null ? pc.getPrimaryPrincipal().toString() : "")){
					isActiveSession = true;
				}
			}
			// 过滤掉的SESSION
			if (filterSession != null && filterSession.getId().equals(session.getId())){
				isActiveSession = false;
			}
			if (isActiveSession){
				sessions.add(session);
			}
		}
		return sessions;
	}
	
}
