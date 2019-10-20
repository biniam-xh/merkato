package edu.mum.mercato.domain;

import javax.persistence.Entity;

@Entity
public class Address {

    private long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
