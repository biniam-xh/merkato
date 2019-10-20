package edu.mum.mercato.domain;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Product {

    private long id;
    private String title;
    private double price;
    private String description;
    private LocalDate createdDate;
    private Category category;
    private User seller;
    private String image;
}
