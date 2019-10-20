package edu.mum.mercato.domain;


import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<User> follow = new ArrayList<>();
}
