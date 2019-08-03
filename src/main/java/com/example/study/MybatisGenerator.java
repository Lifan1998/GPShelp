package com.example.study;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MybatisGenerator {

    public static void main(String[] args) throws Exception {

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("generatorConfig.xml");

        File file = new File("code");
        if (file.exists()) {
            file.delete();
        }
        file.mkdir();
        Properties properties = getProperties();

        ConfigurationParser cp = new ConfigurationParser(properties, warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        if (!warnings.isEmpty()) {
            for (String warn : warnings) {
                System.out.println(warn);
            }
        }
        System.out.println("生成成功！");
    }

    /**
     * 自定义配置
     *
     * @return
     */
    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:mysql://localhost:3307/gpshelp?serverTimezone=UTC");
        properties.setProperty("userId", "root");
        properties.setProperty("password", "202160936");
        // 包名
        properties.setProperty("package", "com.example.study");
        // 要生成的表名
        properties.setProperty("tableName", "administrator");
        properties.setProperty("tableName", "chat_record");
        properties.setProperty("tableName", "receiver");
        properties.setProperty("tableName", "rescuer");
        properties.setProperty("tableName", "task");
        properties.setProperty("tableName", "unit");
        properties.setProperty("tableName", "user");

        return properties;
    }
}
