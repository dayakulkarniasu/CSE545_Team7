package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Transaction;
import com.sbs.sbsgroup7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionStatus(String transactionStatus);

    Transaction findByTransactionId(Long id);

    Transaction findByTransactionOwnerAndTransactionStatus(User user, String transactionStatus);

}