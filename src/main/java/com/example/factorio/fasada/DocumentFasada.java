package com.example.factorio.fasada;

import com.example.factorio.mediator.DocumentMediator;
import com.example.factorio.model.Document;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/document")
public class DocumentFasada {

    private final DocumentMediator documentMediator;

    @RequestMapping(method = RequestMethod.POST, value = "/get")
    public ResponseEntity<?> createDocument(
            @RequestBody Document document,
            HttpServletRequest request
    ){
        Long id = documentMediator.createDocument(document,request.getHeader(HttpHeaders.AUTHORIZATION));
        if (id != null){
            return ResponseEntity.ok("{\"document\":"+id+"}");
        }
        return ResponseEntity.status(400).build();
    }
}
