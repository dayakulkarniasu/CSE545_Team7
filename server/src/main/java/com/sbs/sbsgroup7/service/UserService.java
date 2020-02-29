package com.sbs.sbsgroup7.service;

import java.util.List;

import com.sbs.sbsgroup7.dao.UserDaoInterface;
import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserService {

    @Autowired
    private final UserDaoInterface userDao;

    public UserService(@Qualifier("user") UserDaoInterface userDao) {
        this.userDao = userDao;
    }


    public void add(User userEntity) {
        //userDao.openCurrentSessionwithTransaction();
        userDao.persist(userEntity);
        //userDao.closeCurrentSessionwithTransaction();
    }

    public void update(User userEntity) {
        //userDao.openCurrentSessionwithTransaction();
        userDao.update(userEntity);
        //userDao.closeCurrentSessionwithTransaction();
    }

    public User findById(String id) {
        //userDao.openCurrentSession();
        User user = userDao.findById(id);
        //userDao.closeCurrentSession();
        return user;
    }

    public void delete(String id) {
        //userDao.openCurrentSessionwithTransaction();
        User user = userDao.findById(id);
        userDao.delete(user);
        //userDao.closeCurrentSessionwithTransaction();
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void deleteAll() {
        //userDao.openCurrentSessionwithTransaction();
        userDao.deleteAll();
        //userDao.closeCurrentSessionwithTransaction();
    }

}