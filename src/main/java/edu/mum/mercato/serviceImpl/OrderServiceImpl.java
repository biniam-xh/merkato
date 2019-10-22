package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.repository.OrderRepository;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.service.OrderService;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;


    @Override
    public Order getCart(User buyer) {
        return this.orderRepository.findFirstByBuyer_IdAndOrderStatus(buyer.getId(), OrderStatus.PENDING).orElse(null);
    }

    @Override
    public Order addToCart(Long product_id, int quantity, User buyer ) {
        Product product = productService.findById(product_id);
        List<ProductItem> productsItems = new ArrayList<>();
        List<ProductItem> items = productService.findProductItems(product_id);
        for(int i = 0; i<quantity; i++){
            productsItems.add(items.get(i));
        }
        Order order = getCart(buyer);
        if(order != null){
            productsItems.forEach(order.getProductList()::add);

            double price = productsItems.stream().map(productItem -> productItem.getProduct().getDiscountPrice()).reduce(0.0, (price1,price2)->price1+price2);
            double points = 100;
            double totalPrice = price - points / 100;
            order.setTotalPrice(totalPrice);
            order.setDiscount(points / 100);

            productsItems.forEach(productItem -> productItem.setOrder(order));
            //productsItems.forEach(productItem -> productService.saveItem(productItem));

            return orderRepository.save(order);
        }
        else{
            double totalPrice = 0.0;
            double discount = 0.0;
            Order updatedOrder = new Order(totalPrice,discount,buyer);
            productsItems.forEach(productItem -> productItem.setOrder(updatedOrder));

            return orderRepository.save(updatedOrder);
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

    @Override
    public Order findById(Long l) {
       return orderRepository.findById(l).get();
    }


}
