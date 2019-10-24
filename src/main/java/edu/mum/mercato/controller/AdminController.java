package edu.mum.mercato.controller;

import edu.mum.mercato.Helper.ReviewStatus;
import edu.mum.mercato.config.MerkatoUserDetails;
import edu.mum.mercato.domain.Advert;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.Review;
import edu.mum.mercato.service.AdvertService;
import edu.mum.mercato.service.ProductService;
import edu.mum.mercato.service.ReviewService;
import edu.mum.mercato.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    AdvertService advertService;
    @Autowired
    SecurityService securityService;
    @Autowired
    ReviewService reviewService;

    @ModelAttribute("user")
    public MerkatoUserDetails getDetails(){
        return securityService.findLoggedInUser();
    }

    @GetMapping("")
    public String getHome(){
        return "admin/index";
    }

    @GetMapping("/seller")
    public String sellerPage(Model model){
        List<Product> products = productService.getUnApprovedProducts();
        model.addAttribute("products", products);

        return "admin/seller";
    }

    @GetMapping("approve/{productId}")
    public String approveProduct(@PathVariable("productId") String productId){
        int id=Integer.parseInt(productId);
        productService.deleteProductById(id);
        return "redirect:/admin/seller";
    }
    @GetMapping("delete/{productId}")
    public String deleteProduct(@PathVariable("productId") String productId){
        long id=Long.parseLong(productId);
        Product product=productService.findById(id);
        product.setApproved(true);
        try {
            productService.saveProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/seller";
    }

    @GetMapping("/ads")
    public String getAds(@ModelAttribute Advert advert){
        return "admin/ads";
    }

    @PostMapping("uploadAdd")
    public String uploadAds(@ModelAttribute Advert advert, RedirectAttributes attributes){
        System.out.println(advert);
        advertService.saveAdvert(advert);
        attributes.addFlashAttribute("add_success","Advertisement have been successfully added");
        return "redirect:/admin/ads";
    }

    @GetMapping("/review")
    public String getReview(Model model){
        model.addAttribute("reviewList", reviewService.getProductReviews(ReviewStatus.CREATED));
        return "admin/review";
    }

    @GetMapping("review/approve/{id}")
    public String approveReview(@PathVariable("id") long id){
        Review review=reviewService.findById(id);
        review.setReviewStatus(ReviewStatus.APPROVED);
        reviewService.save(review);
        return "redirect:/admin/review";
    }

    @GetMapping("review/delete/{id}")
    public String deleteReview(@PathVariable("id") long id){
        reviewService.deleteReview(id);
        return "redirect:/admin/review";
    }
}
