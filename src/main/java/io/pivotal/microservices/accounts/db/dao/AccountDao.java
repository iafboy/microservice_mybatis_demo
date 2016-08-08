package io.pivotal.microservices.accounts.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import io.pivotal.microservices.accounts.db.model.AccountModel;

@Repository
public interface AccountDao {
	@Select ("select * from T_ACCOUNT where NUMBER = #{number}")
	public AccountModel findByNumber(@Param("number")String number);
	@Select("select * from T_ACCOUNT where NAME like #{names}")
	public List<AccountModel> findByOwnerContainingIgnoreCase(@Param("names")String names);
	@Select("SELECT count(*) from T_ACCOUNT")
	public int countAccounts();
	@Update("Update T_ACCOUNT set NAME=#{name} where NUMBER=#{no}")
	public boolean updateName(@Param("no")String no,@Param("name")String name);
	@Delete("delete from T_ACCOUNT where NUMBER=#{no}")
	public boolean deleteAccount(@Param("no")String no);
}
