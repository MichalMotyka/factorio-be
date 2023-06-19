package com.example.factorio.repository;

import com.example.factorio.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {


    @Query(value = "Select id from product ORDER BY id DESC LIMIT 1",nativeQuery = true)
    Optional<Document> findLastIndex();
}
