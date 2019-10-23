package edu.mum.mercato.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<Product> productList = new ArrayList<>();

    public void setProductList(Product prodict){
        this.productList.add(prodict);
    }
}
