package com.kameti;

import com.kameti.model.KametiUser;
import com.kameti.model.Role;
import com.kameti.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KametiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KametiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(Role.builder().name("USER").build());
            userService.saveRole(Role.builder().name("ADMIN").build());
            userService.saveRole(Role.builder().name("MANAGER").build());
            userService.saveRole(Role.builder().name("SUPER_ADMIN").build());

            userService.saveUser(KametiUser.builder().name("Gupta Ji").email("wolfgupta@gmail.com").password("r@ndomPa$s").build());
            userService.saveUser(KametiUser.builder().name("Simp Saini").email("simpno1@gmail.com").password("iloveG0ld").build());
            userService.saveUser(KametiUser.builder().name("piyooosh").email("ps1997@gmail.com").password("m@#Ima").build());

            userService.addRoleToUser("wolfgupta@gmail.com", "SUPER_ADMIN");
            userService.addRoleToUser("wolfgupta@gmail.com", "MANAGER");
            userService.addRoleToUser("simpno1@gmail.com", "USER");
            userService.addRoleToUser("simpno1@gmail.com", "ADMIN");
            userService.addRoleToUser("ps1997@gmail.com", "USER ");
        };
    }

}
