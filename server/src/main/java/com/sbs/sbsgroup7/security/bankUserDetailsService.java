package com.sbs.sbsgroup7.security;

import com.sbs.sbsgroup7.DataSource.SystemLogRepository;
import com.sbs.sbsgroup7.DataSource.UserRepository;
import com.sbs.sbsgroup7.model.SessionLog;
import com.sbs.sbsgroup7.model.SystemLog;
import com.sbs.sbsgroup7.model.User;
import com.sbs.sbsgroup7.model.bankUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class bankUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SystemLogRepository systemLogRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Username not found " + userName));

        if (user != null) {
            SessionLog sessionLog = new SessionLog();
            sessionLog.setUserId(user.get().getUserId());
            sessionLog.setTimestamp(new Date());
            user.get().setSessionLog(Arrays.asList(sessionLog));
            userRepository.save(user.get());
            SystemLog systemLog = new SystemLog();
            systemLog.setMessage(userName + " logged in");
            systemLog.setTimestamp(new Date());
            systemLogRepository.save(systemLog);
        }

        return user.map(bankUserDetails::new).get();
    }
}
