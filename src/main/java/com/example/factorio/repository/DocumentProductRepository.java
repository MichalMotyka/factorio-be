package com.example.factorio.repository;

import com.example.factorio.model.Document;
import com.example.factorio.model.DocumentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentProductRepository extends JpaRepository<DocumentProduct,Long> {
    void deleteAllByDocument(Document document);
    List<DocumentProduct> findDocumentProductByDocument(Document document);
}
