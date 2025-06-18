package com.app.drrim.repository;

import com.app.drrim.domain.Gallery;
import com.app.drrim.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends MongoRepository<Gallery, String> {
}
