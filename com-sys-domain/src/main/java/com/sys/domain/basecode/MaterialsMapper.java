package com.sys.domain.basecode;

import com.sys.model.basecode.Materials;
import com.sys.core.domain.IMapper;

import java.util.List;

/**
 * <p>
 * 材料表 Mapper 接口
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
public interface MaterialsMapper extends IMapper<Materials, String> {
    public List<Materials> getAll();
}
