package org.example.springboottestcontainer;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Resource
    private JdbcClient jdbcClient;

    @GetMapping("/users")
    public Iterable<User> listUsers() {
        return jdbcClient.sql("""
                        SELECT `id`, `name`, `sex`
                        FROM `user`
                        """)
                .query(User.class)
                .list();
    }

    @GetMapping("/user/{name}")
    public User getByName(@PathVariable String name) {
        return jdbcClient.sql("""
                        SELECT `id`, `name`, `sex`
                        FROM `user`
                        WHERE `name` = ?
                        """)
                .params(name)
                .query(User.class)
                .single();
    }

    @GetMapping("/user")
    public Iterable<User> listByConditions(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) Integer sex) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        var sql = """
                SELECT `id`, `name`, `sex`
                FROM `user`
                WHERE 1 = 1
                """;

        if (name != null) {
            sql += " AND `name` = :name ";
            params.addValue("name", name);
        }

        if (sex != null) {
            sql += " AND `sex` = :sex ";
            params.addValue("sex", sex);
        }

        sql += """
                ORDER BY `id`
                """;

        return jdbcClient.sql(sql)
                .param("name", name)
                .param("sex", sex)
                .query(User.class)
                .list();
    }

    @GetMapping("/users/in")
    public Iterable<User> listInIds(@RequestParam List<Integer> ids) {
        return jdbcClient.sql("""
                SELECT *
                FROM `user`
                WHERE `id` IN (:ids)
                """)
                .param("ids", ids)
                .query(User.class)
                .list();
    }
}
