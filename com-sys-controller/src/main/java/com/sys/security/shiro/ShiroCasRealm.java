package com.sys.security.shiro;

import com.sys.model.admin.User;
import com.sys.security.cas.CasProperty;
import com.sys.service.admin.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * cas授权与认证
 *
 * @author z.h
 */
public class ShiroCasRealm extends CasRealm {
    // cas服务器地址
    @Value("${cas.casServerUrlPrefix}")
    public String casServerUrlPrefix;
    // 登录成功地址
    @Value("${cas.loginSuccessUrl}")
    public String loginSuccessUrl;

    @Autowired
    private CasProperty casProperty;

    @PostConstruct
    public void initProperty() {
        super.setCasServerUrlPrefix(casServerUrlPrefix);
        // 客户端回调地址
        // setCasService(loginSuccessUrl);
    }

    /**
     * 这个方法用于加载权限
     */
    /*@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg) {
        User user = (User) super.getAvailablePrincipal(arg);
        MenuService menuService = new AnnotationConfigApplicationContext().getBean("munuService");
        Set<String> perms = menuService.listPerms(user.getUserId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        super.onLogout(arg);
        return info;
    }*/


    /**
     * 这个方法用于认证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }

        String ticket = (String) casToken.getCredentials();
        if (ticket == null || ticket.trim().length() == 0) {
            return null;
        }

        TicketValidator ticketValidator = ensureTicketValidator();
        try {
            // contact CAS server to validate service ticket
            String url = casProperty.getShiroServerUrlPrefix() + casProperty.getCasFilterUrlPattern();

            //注意这里大坑，稍后说明
            Assertion casAssertion = ticketValidator.validate(ticket, url);//"http://localhost:8080/index"
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String username = casPrincipal.getName();

//            AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
//            if(authc==null) return null;
//            String username = (String) authc.getPrincipals().getPrimaryPrincipal();
//            String tokens = (String) authc.getCredentials();
            Map<String, Object> map = new HashMap<>(16);
            map.put("username", username);
            IUserService userService = (IUserService) new AnnotationConfigApplicationContext().getBean("userService");
            // 查询用户信息
            User user = userService.findByParams(map);
            // 账号不存在
            if (user == null) {
                throw new UnknownAccountException("账号或密码不正确");
            }
            // 账号锁定
            if (user.getStatus() == 0) {
                throw new LockedAccountException("账号已被锁定,请联系管理员");
            }
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, ticket, getName());
            return info;
        } catch (TicketValidationException e) {
            e.printStackTrace();
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }

    @Override
    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }
}
