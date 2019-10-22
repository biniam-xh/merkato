package edu.mum.mercato.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.mum.mercato.Helper.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product_Orders")
@Getter
@Setter
@NoArgsConstructor
// Order is a service name in sql, it will result in an error
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_Id")
    private long id;
    private double totalPrice;
    private double discount;
    private Enum orderStatus = OrderStatus.PENDING;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="User_ID")
    private User buyer;

    @JsonBackReference
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productList = new ArrayList<>();

    public Order(double totalPrice, double discount, User buyer){
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.buyer = buyer;


    }


}
