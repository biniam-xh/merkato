package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.repository.OrderRepository;
import edu.mum.mercato.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
}
