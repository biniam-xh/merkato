package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.domain.*;
import edu.mum.mercato.repository.OrderRepository;
import edu.mum.mercato.repository.PaymentRepository;
import edu.mum.mercato.repository.ProductRepository;
import edu.mum.mercato.service.OrderService;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public Order getCart(Long id) {
        return this.orderRepository.findFirstByBuyer_IdAndOrderStatus(id, OrderStatus.PENDING).orElse(null);
    }

    @Override
    public Order addToCart(Long product_id, int quantity, User buyer ) {
        Product product = productService.findById(product_id);
        List<ProductItem> productsItems = new ArrayList<>();
        List<ProductItem> items = productService.findProductItems(product_id);
        for(int i = 0; i<quantity; i++){
            productsItems.add(items.get(i));
        }
        Order order = getCart(buyer.getId());
        if(order != null){
            productsItems.forEach(productItem -> productItem.setOrder(order));

            double price = productsItems.stream().map(productItem -> productItem.getProduct().getDiscountPrice()).reduce(0.0, (price1,price2)->price1+price2);
            double points = 100;
            double totalPrice = price;
            order.setTotalPrice(totalPrice);
            order.setDiscount(points / 100);

            return orderRepository.save(order);
        }
        else{
            double price = productsItems.stream().map(productItem -> productItem.getProduct().getDiscountPrice()).reduce(0.0, (price1,price2)->price1+price2);
            double points = 100;
            double totalPrice = price;

            Order updatedOrder = new Order(totalPrice,points / 100,buyer);
            productsItems.forEach(productItem -> productItem.setOrder(updatedOrder));



            return orderRepository.save(updatedOrder);
        }

    }

    @Override
    public Order changeStatus(Long orderId, Enum e) {
        Order order = orderRepository.findById(orderId).get();
        order.setOrderStatus(e);
        return orderRepository.save(order);
    }


    @Override
    public Order findById(Long l) {
       return orderRepository.findById(l).orElse(null);
    }

    @Override
    public void deleteItems(Long productId) {
        Order order = orderRepository.findFirstByBuyer_Id(1L).orElse(null);
        if(order!=null){
            for(ProductItem item: order.getProductList()){
                if(item.getProduct().getId() == productId){
                    item.setOrder(null);
                    productService.saveItem(item);
                }
            }
        }
    }

    @Override
    public Long getProductAmmount(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).get();
        return order.getProductList().stream().filter(productItem -> productItem.getProduct().getId()==productId).count();
    }

    @Override
    public List<Order> getActiveOrders(Long userId, boolean isSeller) {
        if(isSeller){
           List<ProductItem> items = productService.getSellerProductItems(userId);
           List<Order> orders = items.stream().filter(productItem -> productItem.getOrder()!=null)
                   .map(productItem -> productItem.getOrder()).collect(Collectors.toList());
           return orders;
        }
        else{
            return orderRepository.findAllByBuyerIdAndOrderStatus(userId, OrderStatus.ORDERED);
        }
    }

    @Override
    public List<Order> getNonActiveOrders(Long userId, boolean isSeller) {
        if(isSeller){
            List<ProductItem> items = productService.getSellerProductItems(userId);
            List<Order> orders = items.stream().filter(productItem -> productItem.getOrder()!=null)
                    .map(productItem -> productItem.getOrder())
                    .filter(order -> order.getOrderStatus()!=OrderStatus.ORDERED && order.getOrderStatus()!=OrderStatus.PENDING )
                    .collect(Collectors.toList());
            return orders;
        }
        else{
            return orderRepository.findAllByBuyerIdAndOrderStatusNotIn(userId, Arrays.asList(OrderStatus.ORDERED,OrderStatus.PENDING));
        }
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


}
