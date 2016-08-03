package io.pivotal.microservices.accounts.configure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.util.Assert;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import io.pivotal.microservices.common.CommonParams;

@Configuration
@EnableTransactionManagement
public class AccountsMyBatisConfiguration implements TransactionManagementConfigurer {
	protected static Logger logger = Logger.getLogger(AccountsMyBatisConfiguration.class.getName());

	private Properties duridSettings;

	@Autowired
	DataSource dataSource;

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		logger.info("dataSource() invoked");
		try {
			dataSource = DruidDataSourceFactory.createDataSource(duridSettings);
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		logger.info("dataSource() initaled");
		return dataSource;
	}

	public AccountsMyBatisConfiguration() {
		duridSettings = new Properties();
		try {
			File file = new File(this.getClass().getClassLoader().getResource("").getPath() + File.separator
					+ CommonParams.druidPropPath);
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			duridSettings.load(inputStream);
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		logger.info("dataSource configuration initaled");
	}

	@PostConstruct
	public void checkConfigFileExists() {
		if (duridSettings == null) {
			throw new RuntimeException("Cannot find config (please add config file or check your Mybatis configuration)");
		}
	}

	@Bean(name = "sqlSessionFactory")
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactoryBean() {
		logger.info("inital SqlSessionFactory");
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTypeAliasesPackage("io.pivotal.microservices.accounts.db.mybatis.model");
//		Properties properties = new Properties();
//		properties.setProperty("reasonable", "true");
//		properties.setProperty("supportMethodsArguments", "true");
//		properties.setProperty("returnPageInfo", "check");
//		properties.setProperty("params", "count=countSql");

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(
					resolver.getResources("classpath" + CommonParams.BASEPACKAGEPATH + File.separator + "*.xml"));
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