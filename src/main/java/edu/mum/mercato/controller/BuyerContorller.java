package edu.mum.mercato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuyerContorller {

    @GetMapping("/products")
    public String productListing(){
        return "product_list";
    }
}
