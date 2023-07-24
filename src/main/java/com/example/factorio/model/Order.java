package com.example.factorio.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "orders_id_seq",strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private long id;
    private String name;
    private String surname;
    private String company;
    private String pesel;
    private String nip;
    private String adress;
    private String city;
    @Column(name = "postcode")
    private String postCode;
    private String mail;
    private String phone;
    private String description;
    private Deliver deliver;
    @Column(name = "cod")
    private boolean COD;
    @Column(name = "isfacture")
    private boolean isFacture;
}
