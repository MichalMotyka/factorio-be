package com.example.factorioerp.mediator;

import com.example.factorio.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductErpMediator {
    private final ProductService productService;


}
