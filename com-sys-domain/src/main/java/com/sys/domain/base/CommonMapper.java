package com.sys.domain.base;


import com.sys.model.admin.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公共方法
 */
public interface CommonMapper {
    /** 获取菜单 */
    List<Menu>  getAccountMenu(String account);
    Integer validate(@Param("params") Map<String, Object> params);
}
