package com.example.factorio.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Document {
    @Id
    private long id;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String uid;
    private LocalDate createDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    private String description;
}
