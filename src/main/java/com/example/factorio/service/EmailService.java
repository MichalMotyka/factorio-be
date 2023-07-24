package com.example.factorio.service;


import com.example.factorio.configuration.EmailConfiguration;
import com.example.factorio.model.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailConfiguration emailConfiguration;
    @Value("classpath:static/infromacja klient.html")
    Resource templateCustomerMail;

    public void sendEmailToCustomer(Document document){
        try {
            String html = Files.toString(templateCustomerMail.getFile(),Charsets.UTF_8);
            emailConfiguration.sendMail("testerapi715@gmail.com",document.getOrder().getMail(),html,"Nowe zamówienie",true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
