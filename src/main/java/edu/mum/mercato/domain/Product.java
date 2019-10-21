package edu.mum.mercato.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Old_Price")
    private double oldPrice;

    @Column(name = "Discount_Price")
    private double discountPrice;

    @Column(name = "Created_Date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "Seller_Id")
    private User seller;

    @OneToMany(mappedBy = "product")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductItem> productItems = new ArrayList<>();


    public List<ProductImage> getImages() {
        return images;
    }

    public Product(String title, String description, double price, List<String> image_urls) {
        this.title = title;
        this.description = description;
        this.discountPrice = price;
        this.oldPrice = price - 4;
        this.images = new ArrayList<>();
        for(String url: image_urls){
            this.images.add(new ProductImage(url,this));
        }
        this.createdDate = LocalDate.now();
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }
}
