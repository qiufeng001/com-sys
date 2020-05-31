package com.sys.domain.base;


import com.sys.model.admin.Menu;

import java.util.List;

/**
 * 公共方法
 */
public interface CommonMapper {
    /** 获取菜单 */
    List<Menu>  getAccountMenu(String account);

}
