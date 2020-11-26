package com.sys.service.admin.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sys.core.base.Entity;
import com.sys.core.dto.FrontEndFileDto;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.IStatement;
import com.sys.core.query.Query;
import com.sys.core.query.Statement;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.FileUtils;
import com.sys.core.util.UUIDUtils;
import com.sys.domain.basecode.FileMapper;
import com.sys.model.admin.Shop;
import com.sys.domain.admin.ShopMapper;
import com.sys.core.entity.File;
import com.sys.service.admin.IShopService;
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
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-26
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop, String> implements IShopService {
    private static final String FILE_BASE_PATH = "\\nfs\\shop\\";
    @Autowired
    private ShopMapper mapper;
    @Autowired
    private FileMapper fileMapper;

    protected IMapper<Shop, String> getMapper() {
        return mapper;
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
                // 删除前删除本地文件
                Query fileQuery = new Query();
                IStatement statement = new Statement("entityId", ids);
                fileQuery.where(statement);
                List<File> files = fileMapper.selectByParams(query.asMap(), null);
                if(CollectUtils.isNotEmpty(files)) {
                    // 删除本地文件
                    com.sys.service.utils.FileUtils.delFile(files);
                }
                return getMapper().deleteByParams(ids);
            }else {
                return 0;
            }

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
