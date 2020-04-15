package com.sys.code.utils;

import com.sys.core.util.CollectUtils;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * 获取配置文件信息-代码生成器使用
 *
 * @author zhong.h
 */
public class PropertiesUtils {

    public Map<String, String> getProperty() {
        Map<String, String> map = CollectUtils.newHashMap();
        Properties properties = new Properties();
        InputStream in = null;
        try {
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            in = PropertiesUtils.class.getClassLoader().getResourceAsStream("generator.properties");
            // 使用properties对象加载输入流
            properties.load(in);
            //获取key对应的value值
            for (Object proKey : properties.keySet()) {
                String key = (String) proKey;
                String value = (String) properties.get(key);
                map.put(key, value);
            }
            return map;
        } catch (FileNotFoundException e1) {
            return map;
        } catch (IOException e2) {
            return map;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
}
