package edu.mum.mercato.domain;


import javax.persistence.Entity;

@Entity
public class Advert {

    private long id;
    private String title;
    private String content;
    private String image;
}
