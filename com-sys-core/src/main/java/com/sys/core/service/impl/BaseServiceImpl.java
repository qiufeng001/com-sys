package com.sys.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sys.core.base.Entity;
import com.sys.core.domain.IMapper;
import com.sys.core.entity.BaseEntity;
import com.sys.core.entity.IEntity;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.Pagenation;
import com.sys.core.query.Query;
import com.sys.core.service.IService;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.UUIDUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * BaseServiceImpl
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public abstract class BaseServiceImpl<T extends IEntity, K> implements IService<T, K> {

    protected Log logger = LogFactory.getLog(getClass());
    protected final Class<?> idClass = null;
    protected abstract IMapper<T, K> getMapper();

    @Override
    @Transactional(readOnly=true)
    public T findByPrimaryKey(K id) {
        try {
            return getMapper().findByPrimaryKey(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public T findByParam(Query query) {
        try {
            return getMapper().findByParam(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Integer selectCount(Query query) {
        try {
            return getMapper().selectCount(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByPage(Query query, Pagenation page) {
        try {
            return getMapper().selectByPage(query.asMap(), page, query.getSort());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByParams(Query query) {
        try {
            return getMapper().selectByParams(query.asMap(), query.getSort());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(T entry, HttpServletRequest request) {
        initEntry(entry, request);
        try {
            return getMapper().insert(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer batchesInsert(List<T> list) {

        int count = 0;
        for (T entry : list) {

            getMapper().insert(entry);
            count += 1;
        }
        return count;
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer update(T entry, HttpServletRequest request) {
        try {
            BaseEntity<K> item = (BaseEntity<K>) entry;
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();;
            if (principal != null) {
                item.setUpdateUser(principal.getName());
            }
            item.setUpdateTime(new Date());
            return getMapper().update(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByParams(Query query) {
        try {
            Map<String, Object> map = query.asMap();
            String idsStr = (String) map.get("ids");
            if(StringUtils.isNotBlank(idsStr)) {
                List<String> ids = CollectUtils.newArrayList();
                String[] idStrArr = idsStr.split(",");
                for(int i = 0;i < idStrArr.length;i++) {
                    ids.add(idStrArr[i].toString());
                }
                return getMapper().deleteByParams(ids);
            }else {
                return 0;
            }

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByPrimaryKey(K id) {
        try {
            return getMapper().deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Integer validate(Query query) {
        try {
            return getMapper().validate(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Integer delete(T t) throws ServiceException {
        try {
            @SuppressWarnings("unchecked")
            BaseEntity<K> entry = (BaseEntity<K>) t;
            if (entry.getId() != null && !"".equals(entry.getId())) {
                return this.deleteByPrimaryKey(entry.getId());
            }
            return 0;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Integer batchSave(List<T> inserted, List<T> updated, List<T> deleted, HttpServletRequest request) {
        try {

            if (inserted != null) {
                for (T t : inserted) {
                    this.insert(t, request);
                }
            }

            if (updated != null) {
                for (T t : updated) {
                    this.update(t, request);
                }
            }

            if (deleted != null) {

                for (T t : deleted) {
                    this.delete(t);
                }
            }
            return 1;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @SuppressWarnings({"unchecked"})
    protected void initEntry(T entry, HttpServletRequest request) {
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

    @SuppressWarnings("unchecked")
    protected String generateId() {
        return  UUID.randomUUID().toString();
    }
}
