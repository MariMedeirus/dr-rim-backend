package com.app.drrim.services;

import com.app.drrim.domain.Gallery;
import com.app.drrim.dto.CommentDTO;
import com.app.drrim.repository.GalleryRepository;
import com.app.drrim.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository repo;

    public List<Gallery> findAll(){
        return repo.findAll();
    }

    public Gallery findById(String id){
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id));
    }

    public Gallery insert(Gallery obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    private void updateData(Gallery newObj, Gallery obj) {
        newObj.setDate(obj.getDate());
        newObj.setTitle(obj.getTitle());
        newObj.setBody(obj.getBody());
        newObj.setAuthor(obj.getAuthor());
        newObj.setComments(obj.getComments());
    }

    public Gallery update(Gallery obj) {
        Gallery newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public Gallery insertComment(String galleyId, CommentDTO dto) {
        Gallery gallery = repo.findById(galleyId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        gallery.getComments().add(dto);
        return repo.save(gallery);
    }

}
