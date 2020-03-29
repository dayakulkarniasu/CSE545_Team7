package com.sbs.sbsgroup7.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sbs.sbsgroup7.DataSource.UserRepository;

import com.sbs.sbsgroup7.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("user")
public class UserDao implements UserDaoInterface<User, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void persist(User userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void update(User userEntity) {
        Optional<User> foundUser = userRepository.findById(userEntity.getUserId());
        if(foundUser.isPresent()){
            userRepository.save(userEntity);
        }

    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUserId(String id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByEmail(userName).orElse(null);
    }

    @Override
    public void save(User userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void delete(User userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        Iterable<User> it = userRepository.findAll();
        it.forEach(users::add);
        return users;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}