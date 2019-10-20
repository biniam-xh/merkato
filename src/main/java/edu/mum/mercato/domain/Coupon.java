package edu.mum.mercato.domain;

import org.graalvm.compiler.lir.LIRInstruction;

import javax.persistence.Entity;

@Entity
public class Coupon {

    private long id;
    private User buyer;
    private int point;
}
