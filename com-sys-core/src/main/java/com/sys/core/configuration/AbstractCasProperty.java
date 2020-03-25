package com.sys.core.configuration;

/**
 * cas 属性配置类
 */
public abstract class AbstractCasProperty {
    /* Cas服务器地址 */
    private String casServerUrlPrefix;
    /* Cas登录页面地址 */
    private String casLoginUrl;
    /* Cas登出页面地址 */
    private String casLogoutUrl;
    // 当前工程对外提供的服务地址
    private String shiroServerUrlPrefix;
    // casFilter UrlPattern
    private String casFilterUrlPattern;

    private String shiroFilterUrlPattern;
    /*登录地址*/
    private String loginUrl;
    /*登出地址*/
    private String logoutUrl;
    /*登录成功地址*/
    private String loginSuccessUrl;
    /*权限认证失败跳转地址*/
    private String unauthorizedUrl;

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getCasLoginUrl() {
        return casLoginUrl;
    }

    public void setCasLoginUrl(String casLoginUrl) {
        this.casLoginUrl = casLoginUrl;
    }

    public String getCasLogoutUrl() {
        return casLogoutUrl;
    }

    public void setCasLogoutUrl(String casLogoutUrl) {
        this.casLogoutUrl = casLogoutUrl;
    }

    public String getShiroServerUrlPrefix() {
        return shiroServerUrlPrefix;
    }

    public void setShiroServerUrlPrefix(String shiroServerUrlPrefix) {
        this.shiroServerUrlPrefix = shiroServerUrlPrefix;
    }

    public String getCasFilterUrlPattern() {
        return casFilterUrlPattern;
    }

    public void setCasFilterUrlPattern(String casFilterUrlPattern) {
        this.casFilterUrlPattern = casFilterUrlPattern;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getShiroFilterUrlPattern() {
        return shiroFilterUrlPattern;
    }

    public void setShiroFilterUrlPattern(String shiroFilterUrlPattern) {
        this.shiroFilterUrlPattern = shiroFilterUrlPattern;
    }
}
