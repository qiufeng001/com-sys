package com.sys.service.basecode.impl;

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
import com.sys.model.basecode.File;
import com.sys.model.basecode.Materials;
import com.sys.domain.basecode.MaterialsMapper;
import com.sys.service.basecode.IMaterialsService;
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
 * 材料表 服务实现类
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
@Service
public class MaterialsServiceImpl extends BaseServiceImpl<Materials, String> implements IMaterialsService {

    private static final String FILE_BASE_PATH = "\\nfs\\material\\";

    @Autowired
    private MaterialsMapper mapper;
    @Autowired
    private FileMapper fileMapper;

    protected IMapper<Materials, String> getMapper() {
        return mapper;
    }

    @Override
    public List<Materials> getAll() {
        try {
            return mapper.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(Materials entry, HttpServletRequest request) {
        initEntry(entry, request);
        try {
            // 第一步保存配方主数据
            Integer count = getMapper().insert(entry);
            // 第二步保存子表
           this.saveFile(entry, request);
            return count;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer update(Materials entry, HttpServletRequest request) {
        try {
            Entity item = (Entity) entry;
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();;
            if (principal != null) {
                item.setUpdateUser(principal.getName());
            }
            item.setUpdateTime(new Date());
            // 修改前删除文件表
            this.deleteFile(entry);
            // 第二步保存子表
            this.saveFile(entry, request);
            return getMapper().update(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    public void saveFile(Materials entry, HttpServletRequest request) {
        // 第二步保存子表
        List<FrontEndFileDto> dtos = entry.getFiles();
        if(CollectUtils.isNotEmpty(dtos)) {
            for (FrontEndFileDto dto : dtos) {
                String thumbUrl = dto.getThumbUrl();
                String[] thumbUrls = thumbUrl.split("base64,");

                String base64Str = thumbUrls[1];
                String path = FileUtils.decoderToFile(FILE_BASE_PATH, base64Str);
                File file = new File();
                this.initEntry(file, request);
                file.setFileName(dto.getName());
                file.setFilePath(path);
                file.setFileSize(dto.getSize());
                file.setFileType(dto.getType().split("/")[1]);
                file.setBase64Type(thumbUrls[0] + "base64,");
                file.setMaterialId(entry.getId());
                fileMapper.insert(file);
            }
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
                // 删除前删除本地文件
                Query fileQuery = new Query();
                IStatement statement = new Statement("materialId", ids);
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

    private void deleteFile(Materials entity) {
        Query query = new Query();
        IStatement statement = new Statement("materialId", entity.getId());
        query.where(statement);
        List<File> files = fileMapper.selectByParams(query.asMap(), "");
        com.sys.service.utils.FileUtils.delFile(files);
        fileMapper.deleteByParams(query.asMap());
    }

    protected void initEntry(File entry, HttpServletRequest request) {
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
}
