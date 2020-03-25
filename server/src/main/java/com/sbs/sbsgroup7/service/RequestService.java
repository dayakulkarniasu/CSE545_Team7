package com.sbs.sbsgroup7.service;

import com.sbs.sbsgroup7.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    JdbcTemplate template;

    public List<Request> findAll() {
        String sql = "select * from request";
        RowMapper<Request> rm = new RowMapper<Request>() {
            @Override
            public Request mapRow(ResultSet resultSet, int i) throws SQLException {
                Request request = new Request(resultSet.getString("request_id"),
                        resultSet.getString("status"),
                        resultSet.getString("requested_user"),
                        resultSet.getString("approved_user"),
                        resultSet.getString("src_acct"),
                        resultSet.getString("description"),
                        resultSet.getString("request_type"));

                return request;
            }
        };

        return template.query(sql, rm);
    }
}
