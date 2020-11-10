package com.sys.service.basecode;

import com.sys.model.basecode.Materials;
import com.sys.core.service.IService;

import java.util.List;

/**
 * <p>
 * 材料表 服务类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
public interface IMaterialsService extends IService<Materials, String> {
    public List<Materials> getAll();
}
