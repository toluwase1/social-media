package com.example.socialmediaapi;

import com.example.socialmediaapi.request.RegisterRequest;
import com.example.socialmediaapi.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.socialmediaapi.entity.Role.ADMIN;

@SpringBootApplication
public class SocialMediaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .name("Admin")
//                    .lastName("Admin")
//                    .email("admin@mail.com")
//                    .password("password")
////                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = RegisterRequest.builder()
//                    .name("Admin")
//                    .lastName("Admin")
//                    .email("manager@mail.com")
//                    .password("password")
////                    .role(MANAGER)
//                    .build();
//            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//        };
//    }

}
