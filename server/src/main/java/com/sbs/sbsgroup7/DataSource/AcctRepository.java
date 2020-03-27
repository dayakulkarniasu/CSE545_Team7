package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcctRepository extends JpaRepository<Account, String> {

}