package com.sys.core.inspect;

import com.sys.core.configuration.Config;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;

public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 跨域设置
		this.originStting(response);
		String url = request.getRequestURI();
		// GlobalFilter.setResponse(response);

		request.setAttribute("rootpath", request.getContextPath().replaceAll("//", "/"));
//		request.setAttribute("rootpath", ExecutionContext.get(WxConfig.CONTEXT_PATH).replaceAll("//", "/"));

		if (url.indexOf("sys/") >= 0 || url.indexOf(".ig") > 0 || url.indexOf("/druid/") > 0) {
			return true;
		}

//		String userJson = RedisUtils.getUserJsonByToken(request);
		String userJson = "";
		/*String xrequest = request.getHeader("x-requested-with");
		if (userJson == null || userJson.equalsIgnoreCase("")) {
			if ("XMLHttpRequest".equalsIgnoreCase(xrequest)) {// 拦截AJAX请求
				response.setHeader("sessionstatus", "timeout");
			} else {
				response.sendRedirect("/login");
			}
			return false;
		} else {
			//moduleVerify(url, handler, request);
		}*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		Enumeration<String> names = request.getAttributeNames();
		ArrayList<String> vals = new ArrayList<>();
		while(names.hasMoreElements()){
			vals.add(names.nextElement());
		}
		for (String string : vals) {
			if(string.startsWith("@"))
				request.removeAttribute(string);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// GlobalFilter.clear();
		ExecutionContext.getContextMap().put(ExecutionContext.getContextMap().get(Config.CURRENT_THEAD_ID), "");
	}

	/**
	 * 跨域问题解决（react 端加上无效）
	 * @param response
	 */
	private void originStting(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
	}
}