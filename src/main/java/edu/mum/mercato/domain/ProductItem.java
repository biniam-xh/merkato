package edu.mum.mercato.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@Setter
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


    public ProductItem(Product product){
        this.product = product;
    }

}
