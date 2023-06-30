package com.example.factorio.fasada;


import com.example.factorio.model.OrderDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/order")
public class OrderFasada {



    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<?> create(
            @RequestBody OrderDTO orderDTO,
            HttpServletRequest request
    ) {

        return null;
    }
}
