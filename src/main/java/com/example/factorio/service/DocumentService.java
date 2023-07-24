package com.example.factorio.service;


import com.example.factorio.configuration.security.JwtService;
import com.example.factorio.model.*;
import com.example.factorio.repository.DocumentProductRepository;
import com.example.factorio.repository.DocumentRepository;
import com.example.factorio.repository.ProductRepository;
import com.example.factorio.repository.UserRepository;
import com.example.factorio.translator.DocumentDTOToDocument;
import com.example.factorio.translator.DocumentToDocumentDTO;
import com.example.factorio.translator.ProductExtendedToProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    @PersistenceContext
    private EntityManager entityManager;
    private final DocumentRepository documentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DocumentProductRepository documentProductRepository;
    private final DocumentDTOToDocument documentDTOToDocument;
    private final DocumentToDocumentDTO documentDTO;
    private final ProductExtendedToProduct productExtendedToProduct;
    private final ProductService productService;
    private final EmailService emailService;
    private final OrderService orderService;
    private final JwtService jwtService;

    public Document createDocument(DocumentDTO documentDTO, String header) {
        Document document = documentDTOToDocument.toDocument(documentDTO);

        if (document.getDocumentType() == DocumentType.ZAM){

            Order order = orderService.createOrder(document.getOrder());
            document.setOrder(order);

            DocumentDTO documentWM = DocumentDTO.builder()
                    .productList(documentDTO.getProductList())
                    .documentType(DocumentType.WM)
                    .description(" ")
                    .build();

            document.setRelatedDocument(createDocument(documentWM, header));
        }

        LocalDate localDate = LocalDate.now();
        document.setCreateDate(localDate);
        AtomicLong index = new AtomicLong();
        documentRepository.findLastIndex().ifPresentOrElse(value -> {
            index.set(value);
        }, () -> {
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
        document.setUser(user);
        document = documentRepository.saveAndFlush(document);


        Document finalDocument = document;
        documentDTO.getProductList().forEach(value->{
            addProductToDocument(value,finalDocument);
        });

        if (document.getDocumentType() == DocumentType.ZAM){
            documentRepository.findById(document.getRelatedDocument().getId()).ifPresent(value->{
                value.setDescription("Wydanie magazynowe utworzone na podstawie zamówienia "+ finalDocument.getUid());
                documentRepository.save(value);
            });

        }

        return document;
    }

    public void addProductToDocument(ProductExtended product,Document document){
        documentProductRepository.save(new DocumentProduct(0,product.getQuantity(), document,productExtendedToProduct.toProduct(product)));
    }

    public void removeProductsFromDocument(Document document){
        documentProductRepository.deleteAllByDocument(document);
    }

    public boolean changeStatusDocument(String id){
        Document document = documentRepository.findByUid(id).orElse(null);
        if (document != null){
            if (document.getStatus() == Status.PROJECT){
                if (document.getDocumentType() == DocumentType.ZAM){
                    emailService.sendEmailToCustomer(document);
                    //TODO SEND MAIL
                    //TODO VALIDATE TRANSACTION
                    manageOrder(document);
                }
                document.setStatus(Status.NEW);
            }else if (document.getStatus() == Status.NEW){
                document.setStatus(Status.INPROGRES);
            }else if (document.getStatus() == Status.INPROGRES){
                if (document.getDocumentType() == DocumentType.PM || document.getDocumentType() == DocumentType.WM){
                    settleProducts(document);
                }
                document.setEndDate(LocalDate.now());
                document.setStatus(Status.COMPLETE);
            }else{
                return false;
            }
            documentRepository.save(document);
            return true;
        }
        return false;
    }

    @Transactional
    private void settleProducts(Document document){
        documentProductRepository.findDocumentProductByDocument(document).forEach(value->{
            Product product = productRepository.findById(value.getProduct().getId()).orElse(null);
            if (document.getDocumentType() == DocumentType.PM){
                product.setQuantity(product.getQuantity()+value.getQuantity());
            }else if (document.getDocumentType() == DocumentType.WM){
                if (product.getQuantity() < value.getQuantity()){
                    throw new RuntimeException("Brak wystarczającej liczby produktu "+product.getName()+" "+product.getId());
                }
                product.setQuantity(product.getQuantity()-value.getQuantity());
            }
        });
    }
    private boolean manageOrder(Document document){
        settleProducts(document.getRelatedDocument());
        Document documentWM = document.getRelatedDocument();
        documentWM.setStatus(Status.COMPLETE);
        documentRepository.save(documentWM);
        return true;
    }

    public Set<Document> getDocuments(Status status, DocumentType type, String search) {
        StringBuilder sqlQuery = new StringBuilder("SELECT d1_0.id,d1_0.author,d1_0.createdate,d1_0.description,d1_0.documenttype,d1_0.enddate,d1_0.status,d1_0.uid,d1_0.relateddocument,d1_0.orderdoc from document d1_0");
        if (status != null || type != null || (search != null && !search.equals(""))) {
            sqlQuery.append(" where");
            boolean toNext = false;

            if (status != null) {
                sqlQuery.append(" d1_0.status = '").append(status.name()).append("'");
                toNext = true;
            }


            if (type != null) {
                if (toNext) {
                    sqlQuery.append(" AND");
                }
                sqlQuery.append(" d1_0.documenttype = '").append(type.name()).append("'");
                toNext = true;
            }

            if (search != null && !search.equals("")) {
                User user = userRepository.findByLogin(search).orElse(null);
                if (toNext) {
                    sqlQuery.append(" AND");
                }
                sqlQuery.append("(d1_0.description like '%").append(search).append("%'");
                if (user != null) {
                    sqlQuery.append("or author =").append(user.getId());
                }
                sqlQuery.append(" or d1_0.uid like '%").append(search).append("%')");

            }


            Query query = entityManager.createNativeQuery(sqlQuery.toString(), Document.class);
            Set<Document> documentSet = (Set<Document>) query.getResultStream().collect(Collectors.toSet());
            return documentSet.stream().sorted(Comparator.comparingLong(Document::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return null;
    }

    public Set<Document> getDocument(Long id) {
        Set<Document> document = new HashSet<>();
        documentRepository.findById(id).ifPresent(value->{
            document.add(documentDTO.toDocumentDTO(value));
        });
        return document;
    }

    public Document getExtendedDocument(String uid){
       return documentRepository.findByUid(uid).orElse(null);
    }
    public List<Product> getProduct(Document document){
        List<Product> productList = new ArrayList<>();
        documentProductRepository.findDocumentProductByDocument(document).forEach(value->{
           Product product = productService.findProductByIdOrName(value.getProduct().getId(),null);
           product.setQuantity(value.getQuantity());
           productList.add(product);
        });

        return productList;
    }
}
