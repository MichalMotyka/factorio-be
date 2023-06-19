package com.example.factorio.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private long id;
    private String name;
    private String url;
    private float quantity;


}
