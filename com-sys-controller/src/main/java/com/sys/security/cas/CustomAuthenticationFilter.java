package com.sys.security.cas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sys.core.util.CollectUtils;
import org.jasig.cas.client.Protocol;
import org.jasig.cas.client.authentication.*;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.ReflectUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改cas用户验证，如果是没有登录的，给出错误码
 */
public class CustomAuthenticationFilter extends AbstractCasFilter {

    private String casServerLoginUrl;
    private boolean renew;
    private boolean gateway;
    private GatewayResolver gatewayStorage;
    private AuthenticationRedirectStrategy authenticationRedirectStrategy;
    private UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass;
    private static final Map<String, Class<? extends UrlPatternMatcherStrategy>> PATTERN_MATCHER_TYPES = new HashMap();

    public CustomAuthenticationFilter() {
        this(Protocol.CAS2);
    }

    protected CustomAuthenticationFilter(Protocol protocol) {
        super(protocol);
        this.renew = false;
        this.gateway = false;
        this.gatewayStorage = new DefaultGatewayResolverImpl();
        this.authenticationRedirectStrategy = new DefaultAuthenticationRedirectStrategy();
        this.ignoreUrlPatternMatcherStrategyClass = null;
    }

    protected void initInternal(FilterConfig filterConfig) throws ServletException {
        if (!this.isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            String loginUrl = this.getString(ConfigurationKeys.CAS_SERVER_LOGIN_URL);
            if (loginUrl != null) {
                this.setCasServerLoginUrl(loginUrl);
            } else {
                this.setCasServerUrlPrefix(this.getString(ConfigurationKeys.CAS_SERVER_URL_PREFIX));
            }

            this.setRenew(this.getBoolean(ConfigurationKeys.RENEW));
            this.setGateway(this.getBoolean(ConfigurationKeys.GATEWAY));
            String ignorePattern = this.getString(ConfigurationKeys.IGNORE_PATTERN);
            String ignoreUrlPatternType = this.getString(ConfigurationKeys.IGNORE_URL_PATTERN_TYPE);
            Class gatewayStorageClass;
            if (ignorePattern != null) {
                gatewayStorageClass = (Class)PATTERN_MATCHER_TYPES.get(ignoreUrlPatternType);
                if (gatewayStorageClass != null) {
                    this.ignoreUrlPatternMatcherStrategyClass = (UrlPatternMatcherStrategy) ReflectUtils.newInstance(gatewayStorageClass.getName(), new Object[0]);
                } else {
                    try {
                        this.logger.trace("Assuming {} is a qualified class name...", ignoreUrlPatternType);
                        this.ignoreUrlPatternMatcherStrategyClass = (UrlPatternMatcherStrategy)ReflectUtils.newInstance(ignoreUrlPatternType, new Object[0]);
                    } catch (IllegalArgumentException var7) {
                        this.logger.error("Could not instantiate class [{}]", ignoreUrlPatternType, var7);
                    }
                }

                if (this.ignoreUrlPatternMatcherStrategyClass != null) {
                    this.ignoreUrlPatternMatcherStrategyClass.setPattern(ignorePattern);
                }
            }

            gatewayStorageClass = this.getClass(ConfigurationKeys.GATEWAY_STORAGE_CLASS);
            if (gatewayStorageClass != null) {
                this.setGatewayStorage((GatewayResolver)ReflectUtils.newInstance(gatewayStorageClass, new Object[0]));
            }

            Class<? extends AuthenticationRedirectStrategy> authenticationRedirectStrategyClass = this.getClass(ConfigurationKeys.AUTHENTICATION_REDIRECT_STRATEGY_CLASS);
            if (authenticationRedirectStrategyClass != null) {
                this.authenticationRedirectStrategy = (AuthenticationRedirectStrategy)ReflectUtils.newInstance(authenticationRedirectStrategyClass, new Object[0]);
            }
        }

    }

    public void init() {
        super.init();
        String message = String.format("one of %s and %s must not be null.", ConfigurationKeys.CAS_SERVER_LOGIN_URL.getName(), ConfigurationKeys.CAS_SERVER_URL_PREFIX.getName());
        CommonUtils.assertNotNull(this.casServerLoginUrl, message);
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                               final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isRequestUrlExcluded(request)) {
            logger.debug("Request is ignored.");
            filterChain.doFilter(request, response);
            return;
        }

        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = retrieveTicketFromRequest(request);
        final boolean wasGatewayed = this.gateway && this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }

        final String modifiedServiceUrl;

        logger.debug("no ticket and no assertion found");
        if (this.gateway) {
            logger.debug("setting gateway attribute in session");
            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
        } else {
            modifiedServiceUrl = serviceUrl;
        }

        logger.debug("Constructed service url: {}", modifiedServiceUrl);

        final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl,
                getProtocol().getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

        logger.debug("redirecting to \"{}\"", urlToRedirectTo);
        PrintWriter out = response.getWriter();

        response.setContentType("application/json; charset=UTF-8");
        Map<String, Integer> resultMap = CollectUtils.newHashMap();
        resultMap.put("code", 1001);
        out.println(JSON.toJSONString(resultMap));
        //this.authenticationRedirectStrategy.redirect(request, response, urlToRedirectTo);
    }

    public final void setRenew(boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.setCasServerLoginUrl(CommonUtils.addTrailingSlash(casServerUrlPrefix) + "login");
    }

    public final void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public final void setGatewayStorage(GatewayResolver gatewayStorage) {
        this.gatewayStorage = gatewayStorage;
    }

    private boolean isRequestUrlExcluded(HttpServletRequest request) {
        if (this.ignoreUrlPatternMatcherStrategyClass == null) {
            return false;
        } else {
            StringBuffer urlBuffer = request.getRequestURL();
            if (request.getQueryString() != null) {
                urlBuffer.append("?").append(request.getQueryString());
            }

            String requestUri = urlBuffer.toString();
            return this.ignoreUrlPatternMatcherStrategyClass.matches(requestUri);
        }
    }

    public final void setIgnoreUrlPatternMatcherStrategyClass(UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass) {
        this.ignoreUrlPatternMatcherStrategyClass = ignoreUrlPatternMatcherStrategyClass;
    }

    static {
        PATTERN_MATCHER_TYPES.put("CONTAINS", ContainsPatternUrlPatternMatcherStrategy.class);
        PATTERN_MATCHER_TYPES.put("REGEX", RegexUrlPatternMatcherStrategy.class);
        PATTERN_MATCHER_TYPES.put("EXACT", ExactUrlPatternMatcherStrategy.class);
    }
}
