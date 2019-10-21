package edu.mum.mercato.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Product product;

    public Product getProduct() {
        return product;
    }
    public ProductItem(Product product){
        this.product = product;
    }
}
