package com.example.factorio.translator;

import com.example.factorio.model.Product;
import com.example.factorio.model.ProductExtended;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductExtendedToProduct {

    public Product toProduct(ProductExtended extended){
        return toProductMapper(extended);
    }

    @Mappings({
            @Mapping(ignore = true, target = "quantity"),
            @Mapping(ignore = true, target = "accessibility"),
            @Mapping(ignore = true, target = "author")
    })
    protected abstract Product toProductMapper(ProductExtended productExtended);
}
