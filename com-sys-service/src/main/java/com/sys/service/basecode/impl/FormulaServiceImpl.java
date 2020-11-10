package com.sys.service.basecode.impl;

import com.sys.core.base.Entity;
import com.sys.core.exception.ServiceException;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.UUIDUtils;
import com.sys.domain.basecode.FormulaDetailMapper;
import com.sys.model.basecode.Formula;
import com.sys.domain.basecode.FormulaMapper;
import com.sys.model.basecode.FormulaDetail;
import com.sys.service.basecode.IFormulaService;
import com.sys.core.service.impl.BaseServiceImpl;
import com.sys.core.domain.IMapper;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 配方 服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-10
 */
@Service
public class FormulaServiceImpl extends BaseServiceImpl<Formula, String> implements IFormulaService {

    @Autowired
    private FormulaMapper mapper;

    @Autowired
    private FormulaDetailMapper detailMapper;

    protected IMapper<Formula, String> getMapper() {
        return mapper;
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(Formula entry, HttpServletRequest request) {
        initEntry(entry, request);
        try {
            // 第一步保存配方主数据
            Integer formula = getMapper().insert(entry);
            // 第二步保存子表
            List<FormulaDetail> details = entry.getDetails();
            if(CollectUtils.isNotEmpty(details)) {
                for (FormulaDetail detail : details) {
                    this.initEntry(detail, request);
                    detail.setFormulaId(entry.getId());
                    detailMapper.insert(detail);
                }
            }
            return formula;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    protected void initEntry(FormulaDetail entry, HttpServletRequest request) {
        Entity item = (Entity) entry;
        Date date = new Date();
        AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();;
        if (principal != null && item.getCreateTime() == null) {
            item.setCreateTime(date);
            item.setCreateUser(principal.getName());
            item.setUpdateTime(date);
            item.setUpdateUser(item.getCreateUser());
            item.setId(UUIDUtils.getUUID());
        }
        if (idClass != null && idClass.getSimpleName().equals("String")
                && (item.getId() == null || item.getId().toString() == "")) {
            item.setId(generateId());
        }
    }
}
