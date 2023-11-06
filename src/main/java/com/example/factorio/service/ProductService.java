package com.example.factorio.service;

import com.example.factorio.configuration.security.JwtService;
import com.example.factorio.model.Product;
import com.example.factorio.model.ProductResponse;
import com.example.factorio.model.User;
import com.example.factorio.repository.ProductRepository;
import com.example.factorio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public ProductResponse createProduct(Product product, String header) {
        User user = userRepository.findByLogin(jwtService.extractUsername(header)).orElseThrow();
        product.setAuthor(user);
        product.setActive(true);
        product.setAccessibility(false);
        Product productDb = productRepository.saveAndFlush(product);
        return new ProductResponse(productDb.getId());
    }

    public Product findProductByIdOrName(Long id, String name) {
        if (id !=null){
          return productRepository.findById(id).orElseThrow();
        }
        return productRepository.findByName(name).orElseThrow();
    }

    public Product edit(Product product, String header) {
       Product productdb = productRepository.findById(product.getId()).orElseThrow();
       productdb.setActive(false);
       productRepository.save(productdb);
       User user = userRepository.findByLogin(jwtService.extractUsername(header)).orElseThrow();
       product.setAuthor(user);
       product.setId(null);
       product.setActive(true);
       return productRepository.saveAndFlush(product);
    }

    public List<Product> findBySearch(String search, Boolean active){
        Set<Product> products = new HashSet<>();
        if (active != null){
            if (search.isEmpty()){
                products.addAll(productRepository.findByActive(active));
            }else {
                userRepository.findByLogin(search).ifPresent(value -> {
                    products.addAll(productRepository.findByAuthorAndActive(value, active));
                });
                products.addAll(productRepository.findByActiveAndNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(active,search,search));
            }
        }else {
            userRepository.findByLogin(search).ifPresent(value->{
                products.addAll(productRepository.findByAuthor(value));
            });
            products.addAll(productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(search,search));
        }
        products.forEach(System.out::println);
        return products.stream().toList();
    }
}
