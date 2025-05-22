package com.app.drrim.resources;

import com.app.drrim.domain.Post;
import com.app.drrim.domain.User;
import com.app.drrim.dto.UserDTO;
import com.app.drrim.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/posts")
public class PostResource {

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<List<Post>> findAll(){
        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Post> insert(@RequestBody Post obj) {
        Post newObj = service.insert(obj);
        URI uri = URI.create("/posts/" + newObj.getId());
        return ResponseEntity.created(uri).body(newObj);
    }
}
