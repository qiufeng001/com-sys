package com.sys.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sys.core.base.Entity;
import com.sys.core.domain.IMapper;
import com.sys.core.dto.FrontEndFileDto;
import com.sys.core.entity.File;
import com.sys.core.entity.IEntity;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.IStatement;
import com.sys.core.query.Pagenation;
import com.sys.core.query.Query;
import com.sys.core.query.Statement;
import com.sys.core.service.IService;
import com.sys.core.util.CollectUtils;
import com.sys.core.util.FileUtils;
import com.sys.core.util.UUIDUtils;
import com.sys.core.util.UploadFileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * BaseServiceImpl
 *
 * @author zhong.h
 * @date 2019/10/31
 */
public abstract class BaseServiceImpl<T extends IEntity, K> implements IService<T, K> {

    protected Log logger = LogFactory.getLog(getClass());
    protected final Class<?> idClass = null;
    protected abstract IMapper<T, K> getMapper();

    @Override
    @Transactional(readOnly=true)
    public T findByPrimaryKey(K id) {
        try {
            return getMapper().findByPrimaryKey(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public T findByParam(Query query) {
        try {
            return getMapper().findByParam(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Integer selectCount(Query query) {
        try {
            return getMapper().selectCount(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByPage(Query query, Pagenation page) {
        try {
            return getMapper().selectByPage(query.asMap(), page, query.getSort());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectByParams(Query query) {
        try {
            return getMapper().selectByParams(query.asMap(), query.getSort());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insert(T entry, HttpServletRequest request) {
        initEntry(entry, request);
        try {
            return getMapper().insert(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer insertWithFile(T entry, HttpServletRequest request) {
        initEntry(entry, request);
        try {
            // 第一步保存配方主数据
            Integer count = getMapper().insert(entry);
            // 第二步保存子表
            String path = "\\nfs" + java.io.File.separator +
                    entry.getClass().getName().replaceAll(".*\\.","").toLowerCase() +
                    java.io.File.separator;
            this.saveFile(path, entry, request);
            return count;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
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
    public Integer update(T entry, HttpServletRequest request) {
        try {
            Entity item = (Entity) entry;
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();;
            if (principal != null) {
                item.setUpdateUser(principal.getName());
            }
            item.setUpdateTime(new Date());
            return getMapper().update(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer updateWithFile(T entry, HttpServletRequest request) {
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
            this.saveFile("/nfs/" + entry.getName().toLowerCase(), entry, request);
            return getMapper().update(entry);
        } catch (Exception e) {
            throw new ServiceException(e);
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
                return getMapper().deleteByParams(ids);
            }else {
                return 0;
            }

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=false, isolation = Isolation.READ_COMMITTED, rollbackFor=Exception.class)
    public Integer deleteByPrimaryKey(String id) {
        try {
            return getMapper().deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Integer validate(Query query) {
        try {
            return getMapper().validate(query.asMap());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Integer delete(T t) throws ServiceException {
        try {
            @SuppressWarnings("unchecked")
            Entity item = (Entity) t;
            if (item.getId() != null && !"".equals(item.getId())) {
                return this.deleteByPrimaryKey(item.getId());
            }
            return 0;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Integer batchSave(List<T> inserted, List<T> updated, List<T> deleted, HttpServletRequest request) {
        try {

            if (inserted != null) {
                for (T t : inserted) {
                    this.insert(t, request);
                }
            }

            if (updated != null) {
                for (T t : updated) {
                    this.update(t, request);
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

    @SuppressWarnings({"unchecked"})
    protected void initEntry(T entry, HttpServletRequest request) {
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

    protected void saveFile(String filePath, T entry, HttpServletRequest request) {
        // 第二步保存子表
        List<FrontEndFileDto> dtos = entry.getFiles();
        if(CollectUtils.isNotEmpty(dtos)) {
            for (FrontEndFileDto dto : dtos) {
                String thumbUrl = dto.getThumbUrl();
                String[] thumbUrls = thumbUrl.split("base64,");

                String base64Str = thumbUrls[1];
                String path = FileUtils.decoderToFile(filePath, base64Str);
                File file = new File();
                this.initFileEntry(file, request);
                file.setFileName(dto.getName());
                file.setFilePath(path);
                file.setFileSize(dto.getSize());
                file.setFileType(dto.getType().split("/")[1]);
                file.setBase64Type(thumbUrls[0] + "base64,");
                file.setEntityId(entry.getId().toString());
                getMapper().insertWithFile(file);
            }
        }
    }

    protected void initFileEntry(File entry, HttpServletRequest request) {
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

    protected void deleteFile(T entity) {
        List<File> files = getMapper().selectFileByEntityId(entity.getId().toString());
        UploadFileUtils.delFile(files);
        getMapper().deleteFileyEntityId(entity.getId().toString());
    }

    @SuppressWarnings("unchecked")
    protected String generateId() {
        return  UUID.randomUUID().toString();
    }
}
