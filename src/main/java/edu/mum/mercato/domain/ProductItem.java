package edu.mum.mercato.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn
    private Product product;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="Order_Id")
    private Order order;

    public Product getProduct() {
        return product;
    }
    public ProductItem(Product product){
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
