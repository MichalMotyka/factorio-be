package com.example.factorio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDocument{
    private long id;
    private String uid;
    private String name;
    private String surname;
    private String company;
    private String pesel;
    private String nip;
    private String adres;
    private String city;
    private String postCode;
    private String phone;
    private String mail;
    private String description;
}
