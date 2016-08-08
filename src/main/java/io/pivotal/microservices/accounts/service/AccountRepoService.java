package io.pivotal.microservices.accounts.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import io.pivotal.microservices.accounts.db.dao.AccountDao;
import io.pivotal.microservices.accounts.db.model.AccountModel;
@Service(value="AccountRepoService")
//@ComponentScan(basePackages ="io.pivotal.microservices.accounts.db.dao")
//@Import({AccountDao.class})
public class AccountRepoService {
//	@Autowired
//	@Qualifier("AccountDAO")
//	@Resource(name="AccountRepo")
	@Resource
	protected AccountDao accountDao;

	public int countAccounts() {
		return accountDao.countAccounts();
	}

	public AccountModel findByNumber(String accountNumber) {
		return accountDao.findByNumber(accountNumber);
	}

	public List<AccountModel> findByOwnerContainingIgnoreCase(String partialName) {
		return accountDao.findByOwnerContainingIgnoreCase(partialName);
	}
	
	
}
