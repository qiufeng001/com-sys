package com.sys.service.base;

import com.sys.model.admin.Menu;

import java.util.List;

/**
 * 公共服务类
 *
 * @author zhong.h
 */
public interface ICommonService {
    List<Menu> getAccountMenu(String account);
}
