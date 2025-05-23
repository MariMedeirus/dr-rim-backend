package com.app.drrim.services;

import com.app.drrim.domain.Post;
import com.app.drrim.domain.User;
import com.app.drrim.repository.PostRepository;
import com.app.drrim.repository.UserRepository;
import com.app.drrim.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repo;

    public List<Post> findAll(){
        return repo.findAll();
    }

    public Post findById(String id){
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id));
    }

    public Post insert(Post obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    private void updateData(Post newObj, Post obj) {
        newObj.setDate(obj.getDate());
        newObj.setTitle(obj.getTitle());
        newObj.setBody(obj.getBody());
        newObj.setAuthor(obj.getAuthor());
        newObj.setComments(obj.getComments());
    }

    public Post update(Post obj) {
        Post newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public List<Post> findByTitle(String text){
        return repo.findByTitleContainingIgnoreCase(text);
    }
}
