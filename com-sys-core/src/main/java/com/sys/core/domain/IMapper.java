package com.sys.core.domain;

import com.sys.core.entity.File;
import com.sys.core.entity.IEntity;
import com.sys.core.query.Pagenation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;
import java.util.Map;

/**
 * IRepository 底层基础接口
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public interface IMapper<T extends IEntity, K> {
    public T findByPrimaryKey(K id);

    public T findByParam(@Param("params") Map<String, Object> params);

    public Integer selectCount(@Param("params") Map<String, Object> params);

    public List<T> selectByPage(@Param("params") Map<String, Object> params,
                                @Param("page") Pagenation page,
                                @Param("orderby") String orderby);

    public List<T> selectByParams(@Param("params") Map<String, Object> params, @Param("orderby") String orderby);

    public void selectByParams(@Param("params") Map<String, Object> params, @Param("orderby") String orderby, ResultHandler<T> handler);

    public Integer insert(T entry);

    public Integer update(T entry);

    public Integer deleteByParams(@Param("ids") List<String> ids);

    public Integer deleteByParams(@Param("params") Map<String, Object> params);

    public Integer deleteByPrimaryKey(String id);

    /**
     * 验证
     */
    public Integer validate(@Param("params") Map<String, Object> params);

    public Integer insertWithFile(File entry);

    public List<File> selectFileByEntityId(@Param("entityId") String entityId);

    public void deleteFileyEntityId(@Param("entityId") String entityId);
}