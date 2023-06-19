package com.example.factorioerp.fasada;

import com.example.factorio.mediator.ProductMediator;
import com.example.factorioerp.mediator.ProductErpMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/erp/product")
public class ProductFasada {
    private final ProductErpMediator productMediator;

}
