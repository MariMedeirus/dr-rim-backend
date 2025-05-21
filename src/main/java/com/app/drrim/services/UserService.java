package com.app.drrim.services;

import com.app.drrim.domain.User;
import com.app.drrim.dto.UserDTO;
import com.app.drrim.repository.UserRepository;
import com.app.drrim.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findById(String id){
        return repo.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id));
    }

    public User insert(User obj){
       return repo.insert(obj);
    }

    public void delete(String id){
        findById(id);
        repo.deleteById(id);
    }


    public User fromDTO(UserDTO objDTO){
        return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
    }

}
