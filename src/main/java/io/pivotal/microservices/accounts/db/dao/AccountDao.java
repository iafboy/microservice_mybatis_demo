package io.pivotal.microservices.accounts.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import io.pivotal.microservices.accounts.db.model.AccountModel;

/**
 * Repository for Account data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
@Repository
public interface AccountDao {
	/**
	 * Find an account with the specified account number.
	 *
	 * @param accountNumber
	 * @return The account if found, null otherwise.
	 * 
	 */
	@Select ("select * from T_ACCOUNT where NAME = #{name}")
	public AccountModel findByNumber(@Param("name")String accountNumber);

	/**
	 * Find accounts whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching accounts - always non-null, but may be
	 *         empty.
	 */
	@Select("select * from T_ACCOUNT where NAME like #{names}")
	public List<AccountModel> findByOwnerContainingIgnoreCase(@Param("names")String partialName);

	/**
	 * Fetch the number of accounts known to the system.
	 * 
	 * @return The number of accounts.
	 */
	@Select("SELECT count(*) from Account")
	public int countAccounts();
}
