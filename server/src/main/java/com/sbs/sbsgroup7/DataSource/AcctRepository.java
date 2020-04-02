package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AcctRepository extends CrudRepository<Account, Long> {

    Account findByAccountNumber( long accountNumber);

    @Query("select acc from Account acc where acc.user =:user")
    List<Account> findByUser(@Param("user") User user);

    @Query("select acc from Account acc where acc.user =:user")
    Account findOneByUser(@Param("user") User user);

    @Transactional
    @Modifying
    @Query(value="delete from Account a where a.accountNumber = ?1")
    void deleteByAccountNumber(long accountNumber);

    @Transactional
    @Modifying
    @Query(value="update Account set accountType = ?2 where accountNumber = ?1")
    void editByAccountNumber(long accountNumber, String accountType);
}
