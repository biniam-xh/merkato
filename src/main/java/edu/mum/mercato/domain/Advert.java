package edu.mum.mercato.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;

    @Transient
    @JsonIgnore
    private MultipartFile image;

    @OneToOne(mappedBy = "advert", cascade = CascadeType.ALL)
    private AddImage imageUrl;
    private String link;

    public Advert(String title, String content, MultipartFile image, String image_urls, String link) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.imageUrl=new AddImage(image_urls,this);
        this.link = link;
    }

    public void setImageUrl(AddImage imageUrl){
        this.imageUrl=imageUrl;
    }
}
