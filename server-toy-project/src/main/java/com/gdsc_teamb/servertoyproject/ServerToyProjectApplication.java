package com.gdsc_teamb.servertoyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class ServerToyProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerToyProjectApplication.class, args);
    }

}
