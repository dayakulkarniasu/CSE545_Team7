package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByTransactionStatus(String transactionStatus);

    Transaction findByTransactionId(Long id);

}