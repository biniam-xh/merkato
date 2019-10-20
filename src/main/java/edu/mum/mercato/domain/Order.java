package edu.mum.mercato.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_Id")
    private long id;
    private double totalPrice;
    private double discount;
    private String orderStatus;
    @ManyToOne
    @JoinColumn(name="User_ID")
    private User buyer;
    @OneToMany
    @JoinColumn(name="Product_ID")
    private List<Product> productList = new ArrayList<>();

}
