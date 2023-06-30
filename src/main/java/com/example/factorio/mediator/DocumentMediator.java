package com.example.factorio.mediator;

import com.example.factorio.model.*;
import com.example.factorio.service.DocumentService;
import com.example.factorio.translator.DocumentDTOToDocument;
import com.example.factorio.translator.DocumentToDocumentDTO;
import com.example.factorio.translator.ProductToProductExtended;
import com.example.factorio.translator.UserToUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DocumentMediator {
    private final DocumentService documentService;
    private final DocumentToDocumentDTO documentToDocumentDTO;
    private final ProductToProductExtended productToProductExtended;
    private final UserToUserDTO userToUserDTO;

    public Document createDocument(DocumentDTO documentDTO, String header) {
        return documentService.createDocument(documentDTO,header);
    }
    public boolean updateDocumentStatus(String id){
        return documentService.changeStatusDocument(id);
    }
    public Set<Document> getDocuments(Long id,Status status, DocumentType type, String search) {
        Set<Document> documents;
        if (id != null ){
            documents = documentService.getDocument(id);
        }else {
            documents = documentService.getDocuments(status,type,search);
        }
        return documents;
    }

    public DocumentDTO getExtendendDocument(String uid){
       Document document = documentService.getExtendedDocument(uid);
       DocumentDTO documentDTO = documentToDocumentDTO.toDocumentDTO(document);
       List<ProductExtended> productExtendeds = new ArrayList<>();
       documentService.getProduct(document).forEach(value->{
           productExtendeds.add(productToProductExtended.toProductExtended(value));
       });
       documentDTO.setProductList(productExtendeds);
       documentDTO.setAuthor(userToUserDTO.toUserDTO(document.getUser()));
       return documentDTO;
    }
}
