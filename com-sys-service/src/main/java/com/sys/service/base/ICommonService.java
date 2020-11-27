package com.sys.service.base;

import com.sys.core.query.Query;
import com.sys.model.admin.Menu;
import com.sys.model.base.Common;

import java.util.List;

/**
 * 公共服务类
 *
 * @author zhong.h
 */
public interface ICommonService {

    List<Menu> getAccountMenu(String account);

    Integer validate(Query query);
}
