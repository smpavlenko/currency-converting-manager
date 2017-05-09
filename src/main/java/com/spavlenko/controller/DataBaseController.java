package com.spavlenko.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DataBase controller
 *
 * @author sergii.pavlenko
 * @since May 9, 2017
 *
 */
@RestController
@RequestMapping("/v1/db")
public class DataBaseController {
    private static final String LS = System.getProperty("line.separator");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String getDBDump() {
        List<String> userRows = jdbcTemplate.query("select * from user", new RowMapper<String>() {
            public String mapRow(ResultSet rs, int arg1) throws SQLException {
                StringBuilder sb = new StringBuilder();
                sb.append("insert into user (id, date_created, username, password) values (")
                .append(rs.getInt("id")).append(", '")
                .append(rs.getTimestamp("date_created")).append("', '")
                .append(rs.getString("username")).append("', '")
                .append(rs.getString("password")).append("');");

                return sb.toString();
            }
        });
        String usersDump = userRows.stream().reduce("", (t1, t2) -> t1 + LS + t2);

        List<String> exchangeRateRows = jdbcTemplate.query("select * from exchange_rate", new RowMapper<String>() {
            public String mapRow(ResultSet rs, int arg1) throws SQLException {
                StringBuilder sb = new StringBuilder();
                sb.append(
                        "insert into exchange_rate (id, date_created, currency_from, currency_to, rate, user) values (")
                        .append(rs.getInt("id")).append(", '")
                        .append(rs.getTimestamp("date_created")).append("', '")
                        .append(rs.getString("currency_from")).append("', '")
                        .append(rs.getString("currency_to")).append("', ")
                        .append(rs.getString("rate")).append(", ")
                        .append(rs.getString("user")).append(");");

                return sb.toString();
            }
        });
        String exchangeRatesDump = exchangeRateRows.stream().reduce("", (t1, t2) -> t1 + LS + t2);

        return usersDump + LS + exchangeRatesDump;
    }

}
