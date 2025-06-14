package com.app.drrim.resources;

import com.app.drrim.domain.Post;
import com.app.drrim.domain.User;
import com.app.drrim.dto.CommentDTO;
import com.app.drrim.dto.UserDTO;
import com.app.drrim.repository.UserRepository;
import com.app.drrim.resources.util.URL;
import com.app.drrim.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post obj) {
        obj.setId(id);
        Post updatedObj = service.update(obj);
        return ResponseEntity.ok().body(updatedObj);
    }

    @GetMapping("/titlesearch")
    public ResponseEntity<List<Post>> findByName(@RequestParam(value="text", defaultValue="") String text){
        text = URL.decodeParam(text);
        List<Post> list = service.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Post> insertComment(@PathVariable String id, @RequestBody CommentDTO dto) {
        Post updated = service.insertComment(id, dto);
        return ResponseEntity.ok(updated);
    }

}
