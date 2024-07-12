package com.academy.orders.karate.db;

import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbUtils {
    private final JdbcTemplate jdbcTemplate;

    public DbUtils(Map<String, Object> config) {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl((String) config.get("url"));
        dataSource.setUsername((String) config.get("username"));
        dataSource.setPassword((String) config.get("password"));

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void increaseProductQuantity(String uuid){
        jdbcTemplate.update("UPDATE products SET quantity = quantity + 1 WHERE id = ?", UUID.fromString(uuid));
    }

    public Integer getProductQuantity(String uuid) {
        var sql = "SELECT quantity FROM products p WHERE p.id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, UUID.fromString(uuid));
    }
}
