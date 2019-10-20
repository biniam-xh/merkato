package edu.mum.mercato.domain;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    private long id;
    private User buyer;
    private double totalPrice;
    private double discount;
    private List<Product> productList = new ArrayList<>();
}
