package edu.mum.mercato.service;

import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;
import edu.mum.mercato.domain.User;

import java.util.List;

public interface OrderService {
    Order getCart(Long buyerID);
    Order addToCart(Long productId, int quantity, User buyer);
    Order completeOrder(Long orderId);
    Order cancelOrder(Long orderId);
    Order findById(Long l);

    void deleteItems(Long productId);
    Long getProductAmmount(Long orderId, Long productId);

    List<Order> getActiveOrders(Long userId, boolean isSeller);
    List<Order> getNonActiveOrders(Long userId, boolean isSeller);
}
