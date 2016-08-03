package io.pivotal.microservices.services.accounts;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import io.pivotal.microservices.accounts.configure.AccountsMyBatisConfiguration;
import io.pivotal.microservices.accounts.db.dao.AccountDao;


@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(AccountsMyBatisConfiguration.class)
public class AccountsServer {
	protected Logger logger = Logger.getLogger(AccountsServer.class.getName());
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "accounts-server");
		SpringApplication.run(AccountsServer.class, args);
	}
}
