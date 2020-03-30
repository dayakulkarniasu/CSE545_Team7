package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.Cheque;
import com.sbs.sbsgroup7.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChequeRepository extends CrudRepository<Cheque, Long> {
    List<Cheque> findByAccount(Account account);

}
