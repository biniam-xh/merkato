package edu.mum.mercato.domain;

import edu.mum.mercato.Helper.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String content;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Product product;

    private Enum reviewStatus = ReviewStatus.CREATED;
}
