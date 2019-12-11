package com.sys.core.inspect;

import com.fasterxml.jackson.databind.JsonNode;
import com.sys.core.base.DataChangeEntity;
import com.sys.core.util.Helper;
import com.sys.core.util.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DataChangeEntityResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.getParameterType().equals(DataChangeEntity.class)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		@SuppressWarnings("rawtypes")
		DataChangeEntity entry = new DataChangeEntity<>();
		Class<?> cls = Helper.getSuperClassGenricType(parameter.getContainingClass(), 0);

		String content = webRequest.getParameter("datas");
		JsonNode node = JsonUtils.fromJson(content);
		JsonNode json = node.get("inserted");
		if (json != null)
			entry.setInserted(JsonUtils.fromListJson(json.toString(), cls));
		json = node.get("updated");
		if (json != null)
			entry.setUpdated(JsonUtils.fromListJson(json.toString(), cls));
		json = node.get("deleted");
		if (json != null)
			entry.setDeleted(JsonUtils.fromListJson(json.toString(), cls));

		return entry;
	}

}
