package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.SessionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionLogRepository extends JpaRepository<SessionLog, String> {
}
