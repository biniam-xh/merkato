package edu.mum.mercato.domain;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;


}
