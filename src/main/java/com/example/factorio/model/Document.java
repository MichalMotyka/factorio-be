package com.example.factorio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@ToString
@SuperBuilder
public class Document {
    @Id
    @GeneratedValue(generator = "document_id_seq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "document_id_seq", sequenceName = "document_id_seq", allocationSize = 1)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "documenttype")
    private DocumentType documentType;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String uid;
    @Column(name = "createdate")
    private LocalDate createDate;
    @Column(name = "enddate")
    private LocalDate endDate;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "author")
    private User user;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderdoc")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "relateddocument")
    private Document relatedDocument;
}
