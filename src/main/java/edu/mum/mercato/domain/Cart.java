package edu.mum.mercato.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="User_ID")
    private User buyer;
    private double totalPrice;
    private double discount;
    @ManyToOne
    @JoinColumn(name="Product_ID")
    private List<Product> productList = new ArrayList<>();
}
