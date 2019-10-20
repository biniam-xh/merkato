package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.repository.CartRepository;
import edu.mum.mercato.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
}
