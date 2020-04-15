package com.sys.code.excute;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.sys.code.generator.CustomAutoGenerator;
import com.sys.code.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 代码执行器，自动生成代码
 */
public class CodeGeneratorExecute {

    public static void main(String[] args) {
        PropertiesUtils property = new PropertiesUtils();
        Map<String, String> propertiesMap = property.getProperty();
        // 代码生成器
        CustomAutoGenerator mpg = new CustomAutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath);
        gc.setAuthor("zhong.h");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setDateType(DateType.ONLY_DATE);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://115.28.106.80:3306/com_sys?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("mysql@1234");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        String moduleName = propertiesMap.get("moduleName");
        if(StringUtils.isEmpty(moduleName)) {
            System.out.println("生产报错，请设置模块名：设置位置：generator.properties文件的moduleName属性！");
            return;
        }
        pc.setModuleName(moduleName);
//        pc.setParent(projectPath);
        pc.setEntity("com-sys-model\\src\\main\\java\\com\\sys\\model\\" + pc.getModuleName());
        pc.setController("com-sys-controller\\src\\main\\java\\com\\sys\\controller\\" + pc.getModuleName());
        pc.setService("com-sys-service\\src\\main\\java\\com\\sys\\service\\" + pc.getModuleName());
        pc.setServiceImpl("com-sys-service\\src\\main\\java\\com\\sys\\service\\" + pc.getModuleName() + "\\impl");
        pc.setMapper("com-sys-domain\\src\\main\\java\\com\\sys\\domain\\" + pc.getModuleName());
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/com-sys-domain/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/entity.java.vm");
        templateConfig.setService("templates/service.java.vm");
        templateConfig.setServiceImpl("templates/serviceImpl.java.vm");
        templateConfig.setController("templates/controller.java.vm");
        templateConfig.setMapper("templates/mapper.java.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.sys.model.base.entity.Entity");
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);
        // 公共父类控制层
        strategy.setSuperControllerClass("com.sys.core.controller.impl.BaseController");
        strategy.setSuperServiceClass("com.sys.core.service.IService");
        strategy.setSuperServiceImplClass("com.sys.core.service.impl.BaseServiceImpl");
        strategy.setSuperMapperClass("com.sys.core.domain.IMapper");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        String tableName = propertiesMap.get("tableName");
        if(StringUtils.isEmpty(tableName)) {
            System.out.println("生产报错，请设置表名：设置位置：generator.properties文件的tableName属性！");
            return;
        }
        strategy.setInclude(tableName);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
