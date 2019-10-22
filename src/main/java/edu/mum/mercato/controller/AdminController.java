package edu.mum.mercato.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("")
    public String getHome(){
        return "admin/index";
    }

    @GetMapping("/seller")
    public String sellerPage(){
        return "admin/seller";
    }

    @GetMapping("/ads")
    public String getAds(){
        return "admin/ads";
    }

    @GetMapping("/review")
    public String getReview(){
        return "admin/review";
    }
}
