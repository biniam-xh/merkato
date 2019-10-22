package edu.mum.mercato.domain;


import javax.persistence.*;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "User_Id")
    private User buyer;
    private int point = 0;
}
