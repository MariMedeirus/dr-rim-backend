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
}
