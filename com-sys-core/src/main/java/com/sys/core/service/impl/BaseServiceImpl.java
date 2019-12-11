package com.sys.core.service.impl;

import com.sys.core.domain.IRepository;
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

    protected abstract IRepository<T, K> getRepository();

    @Override
    @Transactional(readOnly=true)
    public T findByPrimaryKey(K id) {
        return getRepository().findByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly=true)
    public T findByParam(Query query) {
        return getRepository().findByParam(query.asMap());
    }

    @Override
    @Transactional(readOnly=true)
    public Integer selectCount(Query query) {
        return getRepository().selectCount(query.asMap());
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByPage(Query query, Pagenation page) {
        return getRepository().selectByPage(query.asMap(), page, query.getSort());
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByParams(Query query) {
        return getRepository().selectByParams(query.asMap(), query.getSort());
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(T entry) {
        return getRepository().insert(entry);
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer batchesInsert(List<T> list) {
        int count = 0;
        for (T entry : list) {

            getRepository().insert(entry);
            count += 1;
        }
        return count;
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer update(T entry) {
        return getRepository().update(entry);
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByParams(Query query) {
        return getRepository().deleteByParams(query.asMap());
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByPrimaryKey(K id) {
        return getRepository().deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly=true)
    public Integer validate(Query query) {
        return getRepository().validate(query.asMap());
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
