package com.sys.core.controller.impl;

import com.sys.core.controller.AbstractController;
import com.sys.core.controller.IController;
import com.sys.core.domain.DataChangeEntry;
import com.sys.core.entity.IEntity;
import com.sys.core.exception.JsonManagerException;
import com.sys.core.exception.ServiceException;
import com.sys.core.query.PageResult;
import com.sys.core.query.Pagenation;
import com.sys.core.query.Query;
import com.sys.core.service.IService;
import com.sys.core.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * BaseController
 *
 * @author zhong.h
 * @date 2019/11/1
 */
public abstract class BaseController<T extends IEntity, K> extends AbstractController implements IController<T, K> {

    protected abstract IService<T, K> getService();

    private Class<?> persistentClass;
    @Autowired
    protected Environment env;

    public Class<?> getPersistentClass() {
        return this.persistentClass;
    }

    public BaseController() {
        this.persistentClass = (Class<?>) Helper.getSuperClassGenricType(getClass(), 0);
        Assert.notNull(persistentClass);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @Override
    public T findByPrimaryKey(@PathVariable K id) {
        return getService().findByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    @Override
    public T findByParam(Query query) throws JsonManagerException {
        return getService().findByParam(query);
    }

    @ResponseBody
    @RequestMapping("/list")
    @Override
    public PageResult<T> selectByPage(Query query, Pagenation page) {

        long total = page.getTotal();
        if (total <= 0) {
            total = getService().selectCount(query);
        }
        List<T> rows = getService().selectByPage(query, page);
        return new PageResult<T>(rows, total);
    }

    @ResponseBody
    @RequestMapping("/query")
    @Override
    public List<T> selectByParams(Query query) {
        return getService().selectByParams(query);

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @Override
    public T create(T entry, HttpServletRequest request) {
        getService().insert(entry, request);
        return entry;
    }

    /**
     * 如果是对象种包含集合，需要用这个方法
     * @param entry
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/createByJson")
    @Override
    public T createByJson(@RequestBody T entry, HttpServletRequest request) {
        getService().insert(entry, request);
        return entry;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @Override
    public T update(T entry, HttpServletRequest request) throws JsonManagerException {
        try {
            getService().update(entry, request);
            return entry;
        } catch (ServiceException e) {
            throw new JsonManagerException(e);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/updateByJson")
    @Override
    public T updateByJson(@RequestBody T entry, HttpServletRequest request) throws JsonManagerException {
        try {
            getService().update(entry, request);
            return entry;
        } catch (ServiceException e) {
            throw new JsonManagerException(e);
        }
    }

    /**
     * user/delete?account=admin
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @Override
    public Integer deleteByParams(Query query) {
        return getService().deleteByParams(query);
    }

    /**
     * user/delete/1
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
    @Override
    public Integer deleteByPrimaryKey(@PathVariable("id") String id) {
        return getService().deleteByPrimaryKey(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/batchsave")
    public Integer batchSave(DataChangeEntry<T> datas, HttpServletRequest request) {
        return getService().batchSave(datas.getInserted(), datas.getUpdated(), datas.getDeleted(), request);
    }


    protected OutputStream getOutputFileStream(String fileName, HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        String name = new String(fileName.getBytes("gb2312"), "iso-8859-1");
        // 文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xlsx");

        response.setHeader("Pragma", "no-cache");

        return response.getOutputStream();
    }

}