package com.example.factorio.translator;


import com.example.factorio.model.Product;
import com.example.factorio.model.ProductExtended;
import com.example.factorio.model.User;
import com.example.factorio.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductToProductExtended {
    public ProductExtended toProductExtended(Product product){
        return toExtended(product);
    }

    @Mappings({
            @Mapping(expression = "java(toUserDTO(product.getAuthor()))",target = "author")
    })
    protected abstract ProductExtended toExtended(Product product);

    @Mappings({
            @Mapping(source = "login",target = "name"),
    })
    protected abstract UserDTO toUserDTO(User user);
}
