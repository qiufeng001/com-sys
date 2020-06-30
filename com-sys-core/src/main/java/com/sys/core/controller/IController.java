package com.sys.core.controller;

import com.sys.core.entity.IEntity;
import com.sys.core.query.PageResult;
import com.sys.core.query.Pagenation;
import com.sys.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * IController
 *
 * @author zhong.h
 * @date 2019/11/1
 */
public interface IController <T extends IEntity, K> {

    public T create(T entity, HttpServletRequest request);

    public T update(T entity, HttpServletRequest request);

    public Integer deleteByParams(Query query);

    public Integer deleteByPrimaryKey(String id);

    public T findByParam(Query query);

    public T findByPrimaryKey(K id);

    public List<T> selectByParams(Query query);

    public PageResult<T> selectByPage(Query query, Pagenation page);

}
