package edu.mum.mercato.service;

import edu.mum.mercato.domain.*;

import java.util.List;

public interface OrderService {
    Order getCart(Long buyerID);
    Order addToCart(Long productId, int quantity, User buyer);
    Order changeStatus(Long orderId, Enum e);
    Order findById(Long l);

    void deleteItems(Long productId);
    Long getProductAmmount(Long orderId, Long productId);

    List<Order> getActiveOrders(Long userId, boolean isSeller);
    List<Order> getNonActiveOrders(Long userId, boolean isSeller);

    Payment savePayment(Payment payment);
    Order saveOrder(Order order);
}
