package net.mingsoft.basic.shiro.realm;
import java.util.Collection;
import java.util.Map;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

public class DefautModularRealm extends org.apache.shiro.authc.pam.ModularRealmAuthenticator{
	
	private Map<String, Object> definedRealms;  

    /** 
     * 多个realm实现 
     */  
    @Override  
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {  
        return super.doMultiRealmAuthentication(realms, token);  
    }  
    
    /** 
     * 调用单个realm执行操作 
     */  
    @Override  
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm,AuthenticationToken token) {  
        // 如果该realms不支持(不能验证)当前token  
        if (!realm.supports(token)) {  
            throw new ShiroException("token错误!");  
        }  
        AuthenticationInfo info = null;  
        try {  
            info = realm.getAuthenticationInfo(token);  
            if (info == null) {  
                throw new ShiroException("token不存在!");  
            }  
        } catch (Exception e) {  
            throw new ShiroException("用户名或者密码错误!");  
        }  
        return info;  
    } 


    /** 
     * 判断登录类型执行操作 
     */  
    @Override  
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)throws AuthenticationException {  
        this.assertRealmsConfigured();  
        Realm realm = null;  
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;  
       //判断是否是后台用户
//        if (token.getLogintype().equals(SysConstant.FRONT_LOGIN)) {  
//            realm = (Realm) this.definedRealms.get("systemAuthorizingRealm");  
//        }  
//        else if (token.getLogintype().equals(SysConstant.ADMIN_LOGIN)){
//            realm = (Realm) this.definedRealms.get("userAuthorizingRealm");  
//        }

        return this.doSingleRealmAuthentication(realm, authenticationToken);  
    }  

    /** 
     * 判断realm是否为空 
     */  
    @Override  
    protected void assertRealmsConfigured() throws IllegalStateException {  
        this.definedRealms = this.getDefinedRealms();  
        if (CollectionUtils.isEmpty(this.definedRealms)) {  
            throw new ShiroException("值传递错误!");  
        }  
    }  

    public Map<String, Object> getDefinedRealms() {  
        return this.definedRealms;  
    }  

    public void setDefinedRealms(Map<String, Object> definedRealms) {  
        this.definedRealms = definedRealms;  
    }

}