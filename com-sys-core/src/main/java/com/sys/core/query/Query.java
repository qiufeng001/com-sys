package com.sys.core.query;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询条件定义 查询对象： id：不知道干啥的; name: 也不知道干啥的; conditions： 查询SQL,直接拼装成查询SQL;
 * sorts：SQL的排序字段; groups： SQL的汇总字段;
 *
 * @author kain
 *
 */
public class Query implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6617362131796003330L;
	String id;
	String name;

	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}


	public String getSort() {

		return "";
	}
}
