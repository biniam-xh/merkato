package edu.mum.mercato.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_Id")
    private long id;

    private String status;
    private String chargeId;
    private String balanceTransaction;


    public Payment(String status, String chargeId, String balanceTransaction){
        this.status = status;
        this.chargeId = chargeId;
        this.balanceTransaction = balanceTransaction;

    }
}
