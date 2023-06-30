package com.example.factorio.fasada;

import com.example.factorio.mediator.DocumentMediator;
import com.example.factorio.model.Document;
import com.example.factorio.model.DocumentDTO;
import com.example.factorio.model.DocumentType;
import com.example.factorio.model.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/document")
public class DocumentFasada {

    private final DocumentMediator documentMediator;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<?> createDocument(
            @RequestBody DocumentDTO document,
            HttpServletRequest request
    ){
        Document result = documentMediator.createDocument(document,request.getHeader(HttpHeaders.AUTHORIZATION));
        if (result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(400).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public ResponseEntity<?> getDocument(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) DocumentType type,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long id
    ){
        Set<Document> documents = documentMediator.getDocuments(id,status,type,search);
        if (documents != null && documents.size() != 0){
            return ResponseEntity.ok(documents);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/status")
    public ResponseEntity<?> updateStatus(@RequestParam String id){
        if (documentMediator.updateDocumentStatus(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getExtendedDocument")
    public ResponseEntity<?> getExtendedDocument(@RequestParam String uid){
        return ResponseEntity.ok(documentMediator.getExtendendDocument(uid));
    }
}
