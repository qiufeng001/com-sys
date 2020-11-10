package com.sys.controller.basecode;

import com.sys.core.query.IStatement;
import com.sys.core.query.Q;
import com.sys.core.query.Query;
import com.sys.core.query.Statement;
import com.sys.core.util.CollectUtils;
import com.sys.model.basecode.Formula;
import com.sys.model.basecode.FormulaDetail;
import com.sys.model.basecode.Materials;
import com.sys.service.basecode.IFormulaDetailService;
import com.sys.service.basecode.IMaterialsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sys.core.controller.impl.BaseController;
import com.sys.core.service.IService;
import com.sys.service.basecode.IFormulaService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配方 前端控制器
 * </p>
 *
 * @author zhong.h
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/formula/*")
public class FormulaController extends BaseController<Formula, String> {

    @Autowired
    private IFormulaService service;
    @Autowired
    private IFormulaDetailService detailService;
    @Autowired
    private IMaterialsService materialsService;


    @Override
    protected IService<Formula, String> getService() {
        return service;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @Override
    public Formula findByPrimaryKey(@PathVariable String id) {
        Formula formula = getService().findByPrimaryKey(id);
        // 查询明细
        Query detailQuery = new Query();
        Statement detailStatement = new Statement();
        detailStatement.setName("formulaId");
        detailStatement.setValue(formula.getId());
        detailQuery.where(detailStatement);
        List<FormulaDetail> details = detailService.selectByParams(detailQuery);
        if(CollectUtils.isNotEmpty(details)) {
            List<String> ids = CollectUtils.newArrayList();
            Map<String, FormulaDetail> detailMap = CollectUtils.newHashMap();
            List<FormulaDetail> newDetails = CollectUtils.newArrayList();
            for(FormulaDetail detail : details) {
                detailMap.put(detail.getMaterialId(), detail);
                ids.add(detail.getMaterialId());
            }
            Query query = new Query();
            Statement statement = new Statement();
            statement.setName("ids");
            statement.setValue(ids);
            query.where(statement);
            List<Materials> materials = materialsService.selectByParams(query);
            for(Materials material : materials) {
                FormulaDetail detail = detailMap.get(material.getId());
                detail.setMaterialName(material.getName());
                newDetails.add(detail);
            }
            formula.setDetails(newDetails);
        }
        return formula;
    }
}

