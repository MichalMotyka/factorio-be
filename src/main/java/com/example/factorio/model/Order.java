package com.example.factorio.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @JsonProperty
    @Column(name = "cod")
    private boolean COD;
    @JsonProperty
    @Column(name = "isfacture")
    private boolean isFacture;
}
