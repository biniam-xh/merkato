package edu.mum.mercato.service;

import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;
import edu.mum.mercato.domain.User;

import java.util.List;

public interface OrderService {
    Order getCart(Long buyerID);
    Order addToCart(Long productId, int quantity, User buyer);
    Order completeOrder(User buyer);
    Order cancelOrder(User buyer);
    Order findById(Long l);

    void deleteItems(Long productId);
}
