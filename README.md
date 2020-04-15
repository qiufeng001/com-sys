1.项目是springboot项目，版本2.2.0.RELEASE，项目是maven项目。推荐使用maven3.2.5版本，库管理是阿里云
2.项目采用分项目形式实现mvc模式
3.项目技术
  （1）.springboot
  （2）.mysql
  （3）.mybatis
  
 4.项目启动 
  直接运行类：WebApplicationBootStrap


5.mysql-plus代码生成器(有需要改进的地方，如果新增模块，则不适用，需要修改代码)
com-sys-code-generator模块，修改generator.properties文件
  A.添加需要创建的模块名称：moduleName(模块名称，例如：report)
  B.添加需要生成的关联的表：tableName（表名：例如：t_repor)
  C.执行CodeGeneratorExecute类即可
