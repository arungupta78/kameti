package com.kameti;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KametiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KametiApplication.class, args);
    }

}
