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
import java.util.List;

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
        Product productDb = productRepository.saveAndFlush(product);
        return new ProductResponse(productDb.getId());
    }

    public Product findProductByIdOrName(String id, String name) {
        if (id !=null){
          return productRepository.findById(Long.parseLong(id)).orElseThrow();
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
        List<Product> products = new ArrayList<>();
        if (active != null){
            System.out.println(active);
            userRepository.findByLogin(search).ifPresent(value->{
                products.addAll(productRepository.findByAuthorAndActive(value,active));
            });
            products.addAll(productRepository.findByActiveAndNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(active,search,search));
        }else {
            userRepository.findByLogin(search).ifPresent(value->{
                products.addAll(productRepository.findByAuthor(value));
            });
            products.addAll(productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(search,search));
        }
        return products;
    }
}
