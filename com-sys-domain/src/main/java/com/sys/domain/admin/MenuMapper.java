package com.sys.domain.admin;

import com.sys.core.domain.IMapper;
import com.sys.model.admin.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends IMapper<Menu, String> {
}
