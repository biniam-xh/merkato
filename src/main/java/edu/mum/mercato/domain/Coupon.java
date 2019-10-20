package edu.mum.mercato.domain;

import org.graalvm.compiler.lir.LIRInstruction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private User buyer;
    private int point;
}
