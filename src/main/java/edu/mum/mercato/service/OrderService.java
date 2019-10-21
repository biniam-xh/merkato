package edu.mum.mercato.service;

import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;
import edu.mum.mercato.domain.User;

import java.util.List;

public interface OrderService {
    Order getCart(User user);
    Order addToCart(List<ProductItem> productsItems, User buyer);
    Order completeOrder(User buyer);
    Order cancelOrder(User buyer);
}
