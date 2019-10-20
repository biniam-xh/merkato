package edu.mum.mercato.controller;

import edu.mum.mercato.domain.Product;
import edu.mum.mercato.service.ProductService;
import edu.mum.mercato.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuyerController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String productListing(Model model){
        model.addAttribute("products", productService.getAllProducts() );
        return "buyer/product_list";
    }
}
