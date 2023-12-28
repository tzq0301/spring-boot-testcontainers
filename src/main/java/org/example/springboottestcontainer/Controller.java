package org.example.springboottestcontainer;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/user")
//    public Iterable<User> listByConditions(@RequestParam(required = false) String name,
//                                           @RequestParam(required = false) Integer sex) {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//
//        var conditions = "";
//
//        if (name != null) {
//            conditions += " AND `name` = :name";
//            params.addValue("name", name);
//        }
//
//        if (sex != null) {
//            conditions += " AND `sex` = :sex";
//            params.addValue("sex", sex);
//        }
//
//        return jdbcClient.sql("""
//                        SELECT `id`, `name`, `sex`
//                        FROM `user`
//                        WHERE 1 = 1 %s
//                        ORDER BY `id`
//                            """.formatted(conditions))
//                .paramSource(params)
//                .query(User.class)
//                .list();
//    }

    @GetMapping("/user")
    public Iterable<User> listByConditions(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) Integer sex) {
        return jdbcClient.sql("""
                        SELECT `id`, `name`, `sex`
                        FROM `user`
                        WHERE IF(:name IS NULL, TRUE, `name` = :name)
                          AND IF(:sex IS NULL, TRUE, `sex` = :sex)
                        ORDER BY `id`
                            """)
                .param("name", name)
                .param("sex", sex)
                .query(User.class)
                .list();
    }
}
