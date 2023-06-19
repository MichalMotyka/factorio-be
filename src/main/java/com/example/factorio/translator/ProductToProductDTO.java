package com.example.factorio.translator;


import com.example.factorio.model.Product;
import com.example.factorio.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductToProductDTO {


    public ProductDTO toProductDTO(Product product){
        return productDTO(product);
    }

    @Mappings({
            @Mapping(source = "image", target = "url")
    })
    protected abstract ProductDTO productDTO(Product product);



}
