package edu.mum.mercato.domain;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    private long id;
    private String orderStatus;
    private Cart cart;
}
