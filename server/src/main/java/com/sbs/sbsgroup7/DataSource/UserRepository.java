package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserId(int userId);
    Optional<User> findByEmail(String username);
}
