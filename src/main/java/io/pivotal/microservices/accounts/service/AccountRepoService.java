package io.pivotal.microservices.accounts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.pivotal.microservices.accounts.db.dao.AccountDao;
import io.pivotal.microservices.accounts.db.model.AccountModel;
@Service
public class AccountRepoService {
	@Autowired
	protected AccountDao accountRepository;

	public int countAccounts() {
		return accountRepository.countAccounts();
	}

	public AccountModel findByNumber(String accountNumber) {
		return accountRepository.findByNumber(accountNumber);
	}

	public List<AccountModel> findByOwnerContainingIgnoreCase(String partialName) {
		return accountRepository.findByOwnerContainingIgnoreCase(partialName);
	}
	
	
}
