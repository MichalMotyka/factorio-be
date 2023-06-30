package com.example.factorio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="documentproduct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProduct {
    @Id
    @GeneratedValue(generator = "documentproduct_id_seq",strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(name = "documentproduct_id_seq", sequenceName = "documentproduct_id_seq", allocationSize = 1)
    private long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "document")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
}
