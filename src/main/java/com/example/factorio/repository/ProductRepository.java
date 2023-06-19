package com.example.factorio.repository;

import com.example.factorio.model.Product;
import com.example.factorio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);
    List<Product> findByActive(boolean active);
    List<Product> findByAuthor(User user);
    List<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String search, String search1);
    List<Product> findByActiveAndNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(boolean active,String search, String search1);
    List<Product> findByAuthorAndActive(User user, Boolean active);
}
