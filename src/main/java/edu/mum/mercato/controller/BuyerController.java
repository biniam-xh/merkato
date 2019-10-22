package edu.mum.mercato.controller;

import edu.mum.mercato.domain.Order;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductItem;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.domain.view_models.CartItem;
import edu.mum.mercato.service.OrderService;
import edu.mum.mercato.service.ProductService;
import edu.mum.mercato.service.UserService;
import edu.mum.mercato.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BuyerController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;
    @GetMapping("/products")
    public String productListing(Model model){
        model.addAttribute("products", productService.getAllProducts() );
        return "buyer/product_list";
    }
    @PostMapping("/products/addtocart")
    public @ResponseBody Order addToCart(@RequestBody CartItem item){
          //test user
          User buyer = new User();
          userService.save(buyer);
          return orderService.addToCart(item.getProduct_id(),item.getQuantity(), buyer)    ;

    }
    @GetMapping("/products/test")
    public @ResponseBody List<ProductItem> test(){
        return orderService.findById(1L).getProductList();
    }
}
