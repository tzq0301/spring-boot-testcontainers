package org.example.springboottestcontainer;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Resource
    private JdbcClient jdbcClient;

    @GetMapping("/users")
    public Iterable<User> listUsers() {
        return jdbcClient.sql("""
                        SELECT `id`, `name`
                        FROM `user`
                        """)
                .query(User.class)
                .list();
    }

    @GetMapping("/user/{name}")
    public User getByName(@PathVariable String name) {
        return jdbcClient.sql("""
                        SELECT `id`, `name`
                        FROM `user`
                        WHERE `name` = ?
                        """)
                .params(name)
                .query(User.class)
                .single();
    }
}
