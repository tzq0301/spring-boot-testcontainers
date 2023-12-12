package org.example.springboottestcontainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@SpringBootConfiguration
public class TestApplication {
    @Bean
    @RestartScope
    @ServiceConnection
    @SuppressWarnings("resource")
    public MySQLContainer<?> mysql() {
        return new MySQLContainer<>("mysql:8")
                .withDatabaseName("devdb");
    }

    public static void main(String[] args) {
        SpringApplication
                .from(Application::main)
                .with(TestApplication.class)
                .run(args);
    }
}
