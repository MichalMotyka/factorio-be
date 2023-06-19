package com.example.factorio.service;


import com.example.factorio.configuration.security.JwtService;
import com.example.factorio.model.Document;
import com.example.factorio.model.Status;
import com.example.factorio.model.User;
import com.example.factorio.repository.DocumentRepository;
import com.example.factorio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public long createDocument(Document document, String header) {
        LocalDate localDate = LocalDate.now();
        document.setCreateDate(localDate);

        AtomicLong index = new AtomicLong();
        documentRepository.findLastIndex().ifPresentOrElse(value->{
            index.set(value.getId());
        }, ()->{
            index.set(1);
        });

        StringBuilder stringBuilder = new StringBuilder()
                .append(document.getDocumentType().name())
                .append(localDate.getYear())
                .append(localDate.getMonthValue())
                .append(localDate.getDayOfMonth())
                .append("/")
                .append(index.get());
        document.setUid(stringBuilder.toString());

        document.setStatus(Status.PROJECT);

        User user = userRepository.findByLogin(jwtService.extractUsername(header)).orElseThrow();
        document.setAuthor(user);

       return documentRepository.saveAndFlush(document).getId();
    }
}
