package com.webbdong.sharding.test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * MybatisPlus代码生成器
 */
public class MybatisPlusCodeGenerator {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/db?useSSL=false&useUnicode=true&allowPublicKeyRetrieval=true&characterEncoding=utf8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.cj.jdbc.Driver");

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setEntityLombokModel(true)
                .setTablePrefix("t_")
                .setLogicDeleteFieldName("del_status")
                .setNaming(NamingStrategy.underline_to_camel);

        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor("Webb Dong")
                // 指定输出文件夹位置
                .setOutputDir("D:\\develop\\workspace\\java\\JAVA-01\\Week_08\\lesson15\\sharding-order-2-16\\" +
                        "src\\main\\java\\com\\webbdong\\sharding\\entity")
                .setFileOverride(true)
                .setIdType(IdType.ASSIGN_ID);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.webbdong.sharding")
                .setEntity("entity")
                .setMapper("mapper");

        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }

}
