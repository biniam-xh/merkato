package edu.mum.mercato.domain;

import edu.mum.mercato.Helper.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne
    @JoinColumn(name="User_ID")
    private User buyer;

    @OneToMany
    @JoinColumn(name="Product_ID")
    private List<ProductItem> productList = new ArrayList<>();

    public Order(double totalPrice, double discount, User buyer, List<ProductItem> list){
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.buyer = buyer;
        list.forEach(this.productList::add);

    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<ProductItem> getProductList() {
        return productList;
    }
}
