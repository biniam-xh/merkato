package edu.mum.mercato.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    @Column(name = "First_name")
    private String firstName;

    @NotEmpty
    @Column(name = "Last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "Email")
    private String email;

    @NotEmpty
    @Column(name = "Password")
    private String password;

    @Column(name = "Active")
    private boolean active;


    @OneToOne
    @Valid
    private Role role;
}
