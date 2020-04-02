package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.SessionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionLogRepository extends JpaRepository<SessionLog, String> {
}
