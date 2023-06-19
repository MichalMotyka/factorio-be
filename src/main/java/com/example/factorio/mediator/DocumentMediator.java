package com.example.factorio.mediator;

import com.example.factorio.model.Document;
import com.example.factorio.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentMediator {
    private DocumentService documentService;

    public Long createDocument(Document document, String header) {
        return documentService.createDocument(document,header);
    }
}
