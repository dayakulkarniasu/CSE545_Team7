package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Account;
import com.sbs.sbsgroup7.model.Cheque;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChequeRepository extends CrudRepository<Cheque, Long> {
    List<Cheque> findByAccount(Account account);

    Optional<Cheque> findByCheckNumber(Long checkNumber);
}
