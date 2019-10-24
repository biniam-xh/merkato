package edu.mum.mercato.domain.view_models;

import edu.mum.mercato.domain.Coupon;
import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class OrderViewModel {
    private Order order;
    private List<Product> productList;
    private Coupon coupon;
    private Boolean isSeller;
}
