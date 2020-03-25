package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AcctRepository extends CrudRepository<Account, String> {
}
