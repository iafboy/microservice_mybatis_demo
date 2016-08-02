package io.pivotal.microservices.accounts.configure;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import io.pivotal.microservices.common.CommonParams;
import io.pivotal.microservices.services.accounts.AccountsServer;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db-mybatis-config.properties")
public class AccountsMyBatisConfiguration implements TransactionManagementConfigurer {
	protected Logger logger = Logger.getLogger(AccountsMyBatisConfiguration.class.getName());
//	@Autowired
//	private Properties duridSettings; 
	
	@Autowired
	DataSource dataSource;
	
//	@Bean(name="dataSource")
//	public DataSource dataSource() {
//		logger.info("dataSource() invoked");		
//		try {
//			dataSource=DruidDataSourceFactory.createDataSource(duridSettings);
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			throw new RuntimeException(e);
//		}
//		return dataSource;
//	}
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
    	logger.info("inital SqlSessionFactory");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("io.pivotal.microservices.accounts.db.mybatis.model");

        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath"+CommonParams.BASEPACKAGEPATH+File.separator+"*.xml"));
            logger.info("SqlSessionFactory Created");
            return bean.getObject();
        } catch (Exception e) {
        	logger.log(Level.WARNING, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}