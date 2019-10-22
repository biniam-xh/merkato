package edu.mum.mercato.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
public class Role {
    @Id
    private int id;

    @Column(name = "role")
    private String role;

}
