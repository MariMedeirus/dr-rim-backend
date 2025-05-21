package com.app.drrim.config;

import com.app.drrim.domain.User;
import com.app.drrim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        User alice = new User(null, "Alice Souza", "alice.souza@example.com");
        User bruno = new User(null, "Bruno Lima", "bruno.lima@example.com");
        User carla = new User(null, "Carla Mendes", "carla.mendes@example.com");

        userRepository.saveAll(Arrays.asList(alice, bruno, carla));
    }
}
