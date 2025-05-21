package com.app.drrim.resources;

import com.app.drrim.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        User maria = new User("x","Maria Silva", "maria@gmail.com");
        User joao = new User("y","Joao Silva", "joao@gmail.com");
        List<User> list = new ArrayList<>();
        list.addAll(Arrays.asList(maria, joao));
        return ResponseEntity.ok().body(list);
    }
}
