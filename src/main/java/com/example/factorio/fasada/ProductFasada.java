package com.example.factorio.fasada;

import com.example.factorio.mediator.ProductMediator;
import com.example.factorio.model.Product;
import com.example.factorio.model.ProductDTO;
import com.example.factorio.model.ProductExtended;
import com.example.factorio.model.ProductResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/product")
public class ProductFasada {

    private final ProductMediator productMediator;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody ProductExtended productExtended,
            HttpServletRequest request
    ){
        return ResponseEntity.ok(productMediator.createProduct(productExtended,request.getHeader(HttpHeaders.AUTHORIZATION)));
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/edit")
    public ResponseEntity<ProductResponse> editeProduct(
            @RequestBody ProductExtended productExtended,
            HttpServletRequest request
    ){
        Product product = productMediator.editeProduct(productExtended,request.getHeader(HttpHeaders.AUTHORIZATION));
        return ResponseEntity.ok(new ProductResponse(product.getId()));
    }
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public ResponseEntity<?> getProduct(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active
    ){
        if ((id != null && name != null) || (id == null && name == null)){
            if (search != null){
                return ResponseEntity.ok(productMediator.searchProduct(search,active));
            }
          return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Expected only one parametr");
        }
        return ResponseEntity.ok(productMediator.findProduct(id,name));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAll(
            @RequestParam(required = false) Boolean active
    ){
        return ResponseEntity.ok(productMediator.findAllProduct(active));
    }

}
