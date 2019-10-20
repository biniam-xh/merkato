package edu.mum.mercato.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Price")
    private double price;
    @Column(name = "Description")
    private String description;
    @Column(name = "Created_Date")
    private LocalDate createdDate;
    private String image;
    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;
    @OneToOne
    @JoinColumn(name = "Seller_Id")
    private User seller;
    @OneToMany(mappedBy = "product")
    private List<Review> reviewList = new ArrayList<>();
}
