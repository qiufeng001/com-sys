package com.sys.core.service;

import com.sys.core.entity.IEntity;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.Pagenation;
import com.sys.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * IService
 *
 * @author zhong.h
 * @date 2019/11/1
 */
public interface IService <T extends IEntity, K> {

    public T findByPrimaryKey(K id);

    public T findByParam(Query query);

    public Integer selectCount(Query query);

    public List<T> selectByPage(Query query, Pagenation page);

    public List<T> selectByParams(Query query);

    public Integer insert(T entry, HttpServletRequest request);

    public Integer batchesInsert(List<T> list);

    public Integer update(T entry, HttpServletRequest request);

    public Integer deleteByParams(Query query);

    public Integer deleteByPrimaryKey(String id);

    public Integer validate(Query query);

    public Integer batchSave(List<T> inserted, List<T> updated, List<T> deleted, HttpServletRequest request);
}