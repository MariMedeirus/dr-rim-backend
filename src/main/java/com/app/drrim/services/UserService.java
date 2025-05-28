package com.app.drrim.services;

import com.app.drrim.domain.Post;
import com.app.drrim.domain.User;
import com.app.drrim.dto.LoginDTO;
import com.app.drrim.repository.PostRepository;
import com.app.drrim.repository.UserRepository;
import com.app.drrim.services.exception.EmailAlreadyRegisteredException;
import com.app.drrim.services.exception.LoginException;
import com.app.drrim.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder password;

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findById(String id){
        return repo.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id));
    }

    public User insert(User obj){
        Optional<User> existingUser = repo.findByEmail(obj.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado.");
        }

        String encoder = this.password.encode(obj.getPassword());
        obj.setPassword(encoder);
        return repo.insert(obj);
    }

    public void delete(String id){
        findById(id);
        repo.deleteById(id);
    }

    public User update(User obj){
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        newObj.setCpf(obj.getCpf());
        newObj.setTelephone(obj.getTelephone());

        if (obj.getPassword() != null && !obj.getPassword().isEmpty()) {
            newObj.setPassword(password.encode(obj.getPassword()));
        }
    }

    public User login(LoginDTO loginDTO) {
        return repo.findByEmail(loginDTO.getEmail())
                .filter(user -> password.matches(loginDTO.getPassword(), user.getPassword()))
                .orElseThrow(() -> new LoginException("Email ou senha inválidos"));
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }

    public void addPostToUser(String userId, String postId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado com id: " + postId));

        user.getPosts().add(post);
        repo.save(user);
    }

}
