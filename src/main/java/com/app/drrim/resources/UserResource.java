package com.app.drrim.resources;

import com.app.drrim.domain.Medication;
import com.app.drrim.domain.Post;
import com.app.drrim.domain.Scheduling;
import com.app.drrim.domain.User;
import com.app.drrim.dto.LoginDTO;
import com.app.drrim.dto.UserDTO;
import com.app.drrim.services.UserService;
import com.app.drrim.services.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list = service.findAll();
        List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserDTO> findById(@PathVariable String id){
        User obj = service.findById(id);
        return ResponseEntity.ok().body(new UserDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody User obj) {
        try {
            obj = service.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity <Void> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody User obj, @PathVariable String id) {
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj.getPosts());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            User user = service.login(loginDTO);
            return ResponseEntity.ok(user);
        } catch (LoginException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno no servidor");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}/addPost")
    public ResponseEntity<?> addPostToUser(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            service.addPostToUser(id, body.get("postId"));
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Post não encontrado.");
        }
    }

    @PutMapping("/{id}/addGallery")
    public ResponseEntity<?> addGalleryToUser(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            service.addGalleryToUser(id, body.get("galleryId"));
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Post não encontrado.");
        }
    }

    @PostMapping("/{id}/medication")
    public ResponseEntity<User> insertMedication(@PathVariable String id, @RequestBody Medication medication) {
        User updated = service.insertMedication(id, medication);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{userId}/medication/{medicationId}")
    public ResponseEntity<Medication> findMedication(@PathVariable String userId, @PathVariable String medicationId) {
        Medication medication = service.findMedication(userId, medicationId);
        return ResponseEntity.ok(medication);
    }

    @GetMapping("/{userId}/medication")
    public ResponseEntity<List<Medication>> findAllMedications(@PathVariable String userId) {
        return ResponseEntity.ok(service.findAllMedications(userId));
    }

    @DeleteMapping("/{userId}/medication/{medicationId}")
    public ResponseEntity<Void> deleteMedication(@PathVariable String userId, @PathVariable String medicationId) {
        service.deleteMedication(userId, medicationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/scheduling")
    public ResponseEntity<User> insertScheduling(@PathVariable String id, @RequestBody Scheduling scheduling) {
        User updated = service.insertScheduling(id, scheduling);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{userId}/scheduling/{schedulingId}")
    public ResponseEntity<Scheduling> findScheduling(@PathVariable String userId, @PathVariable String schedulingId) {
        Scheduling scheduling = service.findScheduling(userId, schedulingId);
        return ResponseEntity.ok(scheduling);
    }

    @GetMapping("/{userId}/scheduling")
    public ResponseEntity<List<Scheduling>> findAllSchedulings(@PathVariable String userId) {
        return ResponseEntity.ok(service.findAllSchedulings(userId));
    }

    @DeleteMapping("/{userId}/scheduling/{schedulingId}")
    public ResponseEntity<Void> deleteScheduling(@PathVariable String userId, @PathVariable String schedulingId) {
        service.deleteScheduling(userId, schedulingId);
        return ResponseEntity.noContent().build();
    }

}
