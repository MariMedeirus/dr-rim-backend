package com.app.drrim.resources;

import com.app.drrim.domain.Gallery;
import com.app.drrim.dto.CommentDTO;
import com.app.drrim.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/gallery")
public class GalleryResource {
    @Autowired
    private GalleryService service;


    @GetMapping
    public ResponseEntity<List<Gallery>> findAll(){
        List<Gallery> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> findById(@PathVariable String id){
        Gallery obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Gallery> insert(@RequestBody Gallery obj) {
        Gallery newObj = service.insert(obj);
        URI uri = URI.create("/gallery/" + newObj.getId());
        return ResponseEntity.created(uri).body(newObj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> update(@PathVariable String id, @RequestBody Gallery obj) {
        obj.setId(id);
        Gallery updatedObj = service.update(obj);
        return ResponseEntity.ok().body(updatedObj);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Gallery> insertComment(@PathVariable String id, @RequestBody CommentDTO dto) {
        Gallery updated = service.insertComment(id, dto);
        return ResponseEntity.ok(updated);
    }

}
