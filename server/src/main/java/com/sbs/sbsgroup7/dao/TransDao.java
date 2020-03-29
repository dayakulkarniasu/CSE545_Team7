package com.sbs.sbsgroup7.dao;

//import com.sbs.sbsgroup7.DataSource.TransRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository("transaction")
//public class TransDao implements TransDaoInterface<Transaction, String> {
//
//    @Autowired
//    private TransRepository transRepository;
//
//    @Override
//    public void persist(Transaction transactionEntity) {
//        transRepository.save(transactionEntity);
//    }
//
//    /*
//    @Override
//    public void update(Transaction transactionEntity) {
//        Optional<User> foundUser = userRepository.findById(userEntity.getUserId());
//        if(foundUser.isPresent()){
//            userRepository.save(userEntity);
//        }
//    }
//*/
//
//    @Override
//    public Transaction findById(String id) {
//        return transRepository.findById(id).orElse(null);
//    }
//    /*
//        @Override
//        public  findByUsername(String userName) {
//            return userRepository.findByEmail(userName).orElse(null);
//        }
//     */
//    @Override
//    public void save(Transaction transactionEntity) {
//        transRepository.save(transactionEntity);
//    }
//
//
//    @Override
//    public void delete(Transaction transactionEntity) {
//        transRepository.delete(transactionEntity);
//    }
//
//    @Override
//    public List<Transaction> findAll() {
//        List<Transaction> transactions = new ArrayList<Transaction>();
//        Iterable<Transaction> it = transRepository.findAll();
//        it.forEach(transactions::add);
//        return transactions;
//    }
//
//
//    @Override
//    public void deleteAll() {
//        transRepository.deleteAll();
//    }

//}