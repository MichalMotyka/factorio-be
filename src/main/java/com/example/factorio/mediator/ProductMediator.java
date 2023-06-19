package com.example.factorio.mediator;

import com.example.factorio.model.Product;
import com.example.factorio.model.ProductDTO;
import com.example.factorio.model.ProductExtended;
import com.example.factorio.model.ProductResponse;
import com.example.factorio.repository.ProductRepository;
import com.example.factorio.service.ProductService;
import com.example.factorio.translator.ProductExtendedToProduct;
import com.example.factorio.translator.ProductToProductDTO;
import com.example.factorio.translator.ProductToProductExtended;
import com.example.factorio.translator.UserToUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ProductMediator {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductExtendedToProduct productExtendedToProduct;
    private final ProductToProductExtended productToProductExtended;
    private final ProductToProductDTO productToProductDTO;
    private final UserToUserDTO userToUserDTO;

    public ProductResponse createProduct(ProductExtended productExtended, String header) {
        Product product = productExtendedToProduct.toProduct(productExtended);
        productService.createProduct(product,header);
        return null;
    }

    public ProductExtended findProduct(String id, String name) {
        Product product = productService.findProductByIdOrName(id,name);
        return productToProductExtended.toProductExtended(product);
    }

    public List<ProductDTO> findAllProduct(Boolean active) {
        List<Product> product;
        if (active != null){
            product = productRepository.findByActive(active);
        }else{
            product = productRepository.findAll();
        }
        List<ProductDTO> productDTOList = new ArrayList<>();
        product.forEach(value->{
            productDTOList.add(productToProductDTO.toProductDTO(value));
        });
        return productDTOList;
    }

    public Product editeProduct(ProductExtended productExtended, String header) {
        Product product = productExtendedToProduct.toProduct(productExtended);
        return productService.edit(product,header);
    }

    public List<Product> searchProduct(String search, Boolean active) {
        return productService.findBySearch(search,active);
    }
}
