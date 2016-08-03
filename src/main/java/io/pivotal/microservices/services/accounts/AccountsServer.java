package io.pivotal.microservices.services.accounts;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;

import io.pivotal.microservices.accounts.configure.AccountsMyBatisConfiguration;
import io.pivotal.microservices.accounts.db.dao.AccountDao;
import io.pivotal.microservices.accounts.service.AccountRepoService;


@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(AccountsMyBatisConfiguration.class)
public class AccountsServer {
	protected static Logger logger = Logger.getLogger(AccountsServer.class.getName());
	@Autowired(required=true)
	protected static AccountRepoService accountService;
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "accounts-server");
		SpringApplication.run(AccountsServer.class, args);
		logger.info("app start got the account total number "+accountService.countAccounts());
	
	}
	@RequestMapping("/")
	public String home() {
		logger.info("go to index template file and got the account total number "+accountService.countAccounts());
		return "index";
	}
}
