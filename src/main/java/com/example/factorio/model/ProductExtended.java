package com.example.factorio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductExtended{

    private long id;
    private String name;
    private float price;
    private boolean accessibility;
    private boolean active;
    private int quantity;
    private float width;
    private float height;
    private String image;
    private String[] images;
    private String description;
    private UserDTO author;
    private String category;
}
