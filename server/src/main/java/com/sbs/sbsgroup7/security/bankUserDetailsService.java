package com.sbs.sbsgroup7.security;

import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.model.bankUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class bankUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Username not found " + userName));

        return user.map(bankUserDetails::new).get();
    }
}
