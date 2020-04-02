package com.sbs.sbsgroup7.DataSource;

import com.sbs.sbsgroup7.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemLogRepository extends JpaRepository<SystemLog, String> {
    @Override
    List<SystemLog> findAll();

    @Override
    <S extends SystemLog> S save(S s);
}
