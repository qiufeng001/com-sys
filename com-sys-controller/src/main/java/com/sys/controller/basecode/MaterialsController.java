package com.sys.controller.basecode;

import com.sys.core.dto.FrontEndFileDto;
import com.sys.core.query.Query;
import com.sys.core.query.Statement;
import com.sys.core.util.CollectUtils;
import com.sys.core.entity.File;
import com.sys.model.basecode.Materials;
import com.sys.service.basecode.IFileService;
import com.sys.service.utils.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.basecode.IMaterialsService;

import java.util.List;

/**
 * <p>
 * 材料表 前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/materials/*")
public class MaterialsController extends BaseController<Materials, String> {

    @Autowired
    private IMaterialsService service;

    @Autowired
    private IFileService fileService;

    @Override
    protected IService<Materials, String> getService() {
        return service;
    }

    @ResponseBody
    @RequestMapping("/getAll")
    public List<Materials> getAllMaterials() {
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @Override
    public Materials findByPrimaryKey(@PathVariable String id) {
        Materials material = service.findByPrimaryKey(id);
        // 查询明细
        Query detailQuery = new Query();
        Statement detailStatement = new Statement();
        detailStatement.setName("materialId");
        detailStatement.setValue(material.getId());
        detailQuery.where(detailStatement);
        List<File> files = fileService.selectByParams(detailQuery);
        if(CollectUtils.isNotEmpty(files)) {
            List<FrontEndFileDto> dtos = CollectUtils.newArrayList();
            for(File file : files) {
                FrontEndFileDto dto = FileUtils.encodeBase64(file);
                dtos.add(dto);
            }

            material.setFiles(dtos);
        }
        return material;
    }
            }

