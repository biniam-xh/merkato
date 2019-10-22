package edu.mum.mercato.domain.view_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CartItem {
    private Long product_id;
    private int quantity;

    public Long getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
