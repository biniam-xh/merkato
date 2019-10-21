package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.repository.OrderRepository;
import edu.mum.mercato.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order getCart(User buyer) {
        return this.orderRepository.findFirstByBuyer_IdAndOrderStatus(buyer.getId(), OrderStatus.PENDING).orElse(null);
    }

    @Override
    public Order addToCart(List<ProductItem> productsItems, User buyer ) {
        Optional<Order> order = this.orderRepository.findFirstByBuyer_Id(buyer.getId());
        if(order.isPresent()){
            Order orderItem = order.get();
            productsItems.forEach(orderItem.getProductList()::add);

            double price = productsItems.stream().map(productItem -> productItem.getProduct().getDiscountPrice()).reduce(0.0, (price1,price2)->price1+price2);
            double points = 100;
            double totalPrice = price - points / 100;
            orderItem.setTotalPrice(totalPrice);
            orderItem.setDiscount(points / 100);

            return orderRepository.save(orderItem);
        }
        else{
            double totalPrice = 0.0;
            double discount = 0.0;
            return new Order(totalPrice,discount,buyer,productsItems);
        }

    }

    @Override
    public Order completeOrder(User buyer) {
        return null;
    }

    @Override
    public Order cancelOrder(User buyer) {
        return null;
    }


}
