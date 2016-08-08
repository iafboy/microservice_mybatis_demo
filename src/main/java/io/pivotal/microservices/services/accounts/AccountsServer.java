package io.pivotal.microservices.services.accounts;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import io.pivotal.microservices.common.CommonParams;

@EnableAutoConfiguration
@EnableDiscoveryClient
//@Import(AccountsMyBatisConfiguration.class)
//@ComponentScan(basePackages = CommonParams.BASEACCOUNTPATH)
@ComponentScan(basePackages = {"io.pivotal.microservices.accounts","io.pivotal.microservices.accounts.configure","io.pivotal.microservices.accounts.service","io.pivotal.microservices.accounts.controller","io.pivotal.microservices.accounts.db.dao"})
public class AccountsServer {
	protected static Logger logger = Logger.getLogger(AccountsServer.class.getName());
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "accounts-server");
		SpringApplication.run(AccountsServer.class, args);
	
	}
	
}
