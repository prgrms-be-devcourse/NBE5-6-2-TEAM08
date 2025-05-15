package com.grepp.team08.app.model.place.repository;

import com.grepp.team08.app.model.place.document.HotPlaceEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotPlaceEmbeddingRepository extends MongoRepository<HotPlaceEmbedding, String> {

}
