package edu.mum.mercato.domain;

import javax.persistence.Entity;

@Entity
public class Review {

    private long id;
    private String title;
    private String content;
}
