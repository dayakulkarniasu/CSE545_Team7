package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface AcctRepository extends CrudRepository<Account, Long> {

    Account findByAccountNumber( long accountNumber);

    @Query("select acc from Account acc where acc.user =:user")
    List<Account> findByUser(@Param("user") User user);


}
