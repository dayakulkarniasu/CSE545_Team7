package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
