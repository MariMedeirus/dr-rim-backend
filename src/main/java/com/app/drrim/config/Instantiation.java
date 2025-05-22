package com.app.drrim.config;

import com.app.drrim.domain.Post;
import com.app.drrim.domain.User;
import com.app.drrim.dto.AuthorDTO;
import com.app.drrim.dto.CommentDTO;
import com.app.drrim.repository.PostRepository;
import com.app.drrim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User alice = new User(null, "Alice Souza", "alice.souza@example.com", "12345678909", "11940580736", "Alice123");
        User bruno = new User(null, "Bruno Lima", "bruno.lima@example.com", "12345678800", "11940599736", "Bolo123");
        User carla = new User(null, "Carla Mendes", "carla.mendes@example.com", "12345678909", "11940580736", "Carlinhos#123");

        userRepository.saveAll(Arrays.asList(alice, bruno, carla));

        Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu férias", "Vou viajar para o Rio!", new AuthorDTO(alice));
        Post post2 = new Post(null, sdf.parse("19/03/2018"), "Boa noite", "Boa noite e que Deus abençoe!", new AuthorDTO(alice));

        CommentDTO c1= new CommentDTO("Boa Viagem!",sdf.parse("21/03/2018") , new AuthorDTO(bruno));
        CommentDTO c2= new CommentDTO("Aproveita!",sdf.parse("21/03/2018") , new AuthorDTO(carla));
        CommentDTO c3= new CommentDTO("Boa noite",sdf.parse("21/03/2018") , new AuthorDTO(bruno));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().addAll(Arrays.asList(c3));

        postRepository.saveAll(Arrays.asList(post1, post2));

        alice.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(alice);
    }
}
