package edu.mum.mercato.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class AddImage {
    @Id
    private Long id;
    private String imageURL;
    @OneToOne(cascade = CascadeType.ALL)
    private Advert advert;

    public AddImage(String url, Advert advert){
        this.imageURL = url;
        this.advert = advert;
    }

}
