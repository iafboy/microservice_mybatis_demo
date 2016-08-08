package io.pivotal.microservices.accounts.configure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.pivotal.microservices.common.CommonParams;

import java.util.logging.Logger;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@AutoConfigureAfter(AccountsMyBatisConfiguration.class)
public class MyBatisMapperScannerConfig {
	private Logger logger = Logger.getLogger(MyBatisMapperScannerConfig.class.getName());
	@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("io.pivotal.microservices.accounts.db.dao");
        logger.info("mapperScannerConfigurer created");
        return mapperScannerConfigurer;
    }
}
