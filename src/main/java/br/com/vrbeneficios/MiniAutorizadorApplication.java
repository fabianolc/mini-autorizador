package br.com.vrbeneficios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MiniAutorizadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniAutorizadorApplication.class, args);
    }
}
