package com.sys.core.service.impl;

import com.sys.core.domain.IMapper;
import com.sys.core.entity.BaseEntity;
import com.sys.core.entity.IEntity;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.Pagenation;
import com.sys.core.query.Query;
import com.sys.core.service.IService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BaseServiceImpl
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public abstract class BaseServiceImpl<T extends IEntity, K> implements IService<T, K> {

    protected Log logger = LogFactory.getLog(getClass());

    protected abstract IMapper<T, K> getMapper();

    @Override
    @Transactional(readOnly=true)
    public T findByPrimaryKey(K id) {
        return getMapper().findByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly=true)
    public T findByParam(Query query) {
        return getMapper().findByParam(query.asMap());
    }

    @Override
    @Transactional(readOnly=true)
    public Integer selectCount(Query query) {
        return getMapper().selectCount(query.asMap());
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByPage(Query query, Pagenation page) {
        return getMapper().selectByPage(query.asMap(), page, query.getSort());
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByParams(Query query) {
        return getMapper().selectByParams(query.asMap(), query.getSort());
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(T entry) {
        return getMapper().insert(entry);
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
    public Integer update(T entry) {
        return getMapper().update(entry);
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByParams(Query query) {
        return getMapper().deleteByParams(query.asMap());
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByPrimaryKey(K id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly=true)
    public Integer validate(Query query) {
        return getMapper().validate(query.asMap());
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
    public Integer batchSave(List<T> inserted, List<T> updated, List<T> deleted) throws ServiceException {
        try {

            if (inserted != null) {
                for (T t : inserted) {
                    this.insert(t);
                }
            }

            if (updated != null) {
                for (T t : updated) {
                    this.update(t);
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

}
