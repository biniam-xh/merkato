package edu.mum.mercato.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private long numberOfCopies;

//    private String productCategoryName;


     @Transient
     @JsonIgnore
     private MultipartFile imageData;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;


    @OneToOne
    @JoinColumn(name = "Seller_Id")
    private User seller;

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    private List<Review> reviewList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductItem> productItems = new ArrayList<>();

    @Transient
    private Long copiesCount;

    @Transient
    private int orderedAmount;


    @Transient
    private int ratingsAvg;

    @Column(name = "Approved")
    private boolean approved;


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
        this.approved=false;
    }

    public void setImages(ProductImage productImage){
        this.images.add(productImage);
    }


    public Long getCopiesCount(){
        return getProductItems().stream().filter(productItem -> productItem.getOrder()==null).count();
    }
    public int getRatingsAvg(){
        if(!getReviewList().isEmpty()){
            int r =  getReviewList().stream().map(review -> review.getRating()).reduce(0,(a,b)->a+b);
            return r / getReviewList().size();
        }
        return 0;

    }
}
