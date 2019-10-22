package edu.mum.mercato.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

//    @Lob
//    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
//    private byte[] image;

    private String imageURL;

    @OneToOne
    @JoinColumn(name = "Product_Id")
    private Product product;

//    public ProductImage(String url, Product product){
//        this.imageURL = url;
//        this.product = product;
//    }
//
//    public String getImageURL() {
//        return imageURL;
//    }
}


