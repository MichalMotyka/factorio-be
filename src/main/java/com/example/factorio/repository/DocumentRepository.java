package com.example.factorio.repository;

import com.example.factorio.model.Document;
import com.example.factorio.model.DocumentType;
import com.example.factorio.model.Status;
import com.example.factorio.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    @Query(value = "Select id from document ORDER BY id DESC LIMIT 1",nativeQuery = true)
    Optional<Integer> findLastIndex();


    List<Document> findDocumentsByUidLikeIgnoreCaseOrUserOrCreateDateOrDescriptionLikeIgnoreCase(String uid, User author,String createDate, String description);


    List<Document> findByDocumentTypeOrStatus(DocumentType documenttype, Status status);
    Optional<Document> findByUid(String uid);

}
