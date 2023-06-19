package com.example.factorio.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product{
    @Id
    @GeneratedValue(generator = "product_id_seq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private float price;
    private int quantity;
    private boolean accessibility;
    private boolean active;
    private String image;
    private String[] images;
    private String description;
    private float width;
    private float height;
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    private String category;
}
