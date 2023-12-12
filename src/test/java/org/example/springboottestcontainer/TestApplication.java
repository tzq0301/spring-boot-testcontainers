package org.example.springboottestcontainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

@SpringBootConfiguration
public class TestApplication {
    @Bean
    @ServiceConnection
    @SuppressWarnings("resource")
    public MySQLContainer<?> mySQL() {
        return new MySQLContainer<>("mysql:8")
                .withDatabaseName("devdb")
                .withUsername("devusername")
                .withPassword("devpassword");
    }

    public static void main(String[] args) {
        SpringApplication
                .from(Application::main)
                .with(TestApplication.class)
                .run(args);
    }
}
