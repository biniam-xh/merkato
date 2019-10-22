package edu.mum.mercato.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private LocalDate createdDate = LocalDate.now();

    @Column(name = "Number_OF_Copies")
    private int numberOfCopies;

    private String productCategoryName;


     @Transient
     @JsonIgnore
     private MultipartFile imageData;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

//    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
//    private ProductImage productImage;
//    private String image;

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



    public void setImages(ProductImage productImage){
        this.images.add(productImage);
    }


}
