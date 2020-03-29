package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransRepository extends JpaRepository<Transaction, String> {

}