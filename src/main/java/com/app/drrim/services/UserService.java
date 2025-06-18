package com.app.drrim.services;

import com.app.drrim.domain.*;
import com.app.drrim.dto.LoginDTO;
import com.app.drrim.repository.GalleryRepository;
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
    private GalleryRepository galleryRepository;

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

    public void addGalleryToUser(String userId, String galleryId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com id: " + userId));

        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado com id: " + galleryId));

        user.getGallery().add(gallery);
        repo.save(user);
    }

    public User insertMedication(String id, Medication medication) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.getMedication().add(medication);
        return repo.save(user);
    }

    public User insertScheduling(String id, Scheduling scheduling) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.getScheduling().add(scheduling);
        return repo.save(user);
    }

    public void deleteMedication(String userId, String medicationId){
        User user = repo.findById(userId).
                orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        if (!user.getMedication().removeIf(med -> medicationId.equals(med.getId())))
            throw new RuntimeException("Medication não encontrada com id: " + medicationId);
        repo.save(user);
    }

    public void deleteScheduling(String userId, String schedulingId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!user.getScheduling().removeIf(s -> schedulingId.equals(s.getId())))
            throw new RuntimeException("Scheduling não encontrado com id: " + schedulingId);
        repo.save(user);
    }


}
