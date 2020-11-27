package com.sys.controller.base;

import com.sys.core.controller.impl.BaseController;
import com.sys.core.query.Query;
import com.sys.core.service.IService;
import com.sys.core.util.CollectUtils;
import com.sys.model.admin.Menu;
import com.sys.model.base.Common;
import com.sys.model.base.Dictions;
import com.sys.service.base.ICommonService;
import com.sys.service.base.IDictionsService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Queue;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/common/*")
public class CommonController {

    @Autowired
    private ICommonService commonService;

    /** 获取菜单 */
    @ResponseBody
    @RequestMapping("/getMenu")
    public List<Menu> getMenu(HttpServletRequest request) {
        List<Menu> menus = CollectUtils.newArrayList();
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();;
        if (principal != null) {
            menus = commonService.getAccountMenu(principal.getName());
        }
        return menus;
    }

    /** 验证名称或者code是否重复 */
    @ResponseBody
    @RequestMapping("/validate")
    public Integer validate(Query query, HttpServletRequest request) {
        return commonService.validate(query);
    }
}

