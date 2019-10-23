package edu.mum.mercato.controller;

import edu.mum.mercato.domain.*;
import edu.mum.mercato.domain.view_models.CartItem;
import edu.mum.mercato.domain.view_models.CartModalView;
import edu.mum.mercato.service.*;
import edu.mum.mercato.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BuyerController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecurityService  securityService;

    @Autowired
    AdvertService advertService;

    @Autowired
    UserService userService;
    @GetMapping("/products")
    public String productListing(Model model){
        model.addAttribute("user",securityService.findLoggedInUser());
        model.addAttribute("products", productService.getAllProducts() );
        Order order = orderService.getCart(1L);
        if(order!=null){
            model.addAttribute("productItems", order.getProductList());
            System.out.println("");
        }

        Advert advert=advertService.findOneAdvert();
        System.out.println(advert);
        model.addAttribute("advert",advert);


        return "buyer/product_list";
    }
    @PostMapping("/products/addtocart")
    public @ResponseBody int addToCart(@RequestBody CartItem item){
          //test user
          User buyer = userService.findById(1L);
          if(buyer==null)buyer = new User(); userService.save(buyer);

          Order order = orderService.addToCart(item.getProduct_id(),item.getQuantity(), buyer);

        //temp response
            int count = productService.getProductsInCartCount(order.getId());
          return count;

    }
    @GetMapping("/products/test")
    public @ResponseBody List<ProductItem> test(){
        return orderService.findById(1L).getProductList();
    }

    @GetMapping("/products/cart")
    public String cart(Model model){
        Order order = orderService.findById(1L);
        if(order!=null){
            List<Product> products = order.getProductList().stream()
                    .map(productItem -> productItem.getProduct()).distinct().collect(Collectors.toList());

            products.stream().forEach(product -> product.setOrderedAmount(Math.toIntExact(orderService.getProductAmmount(order.getId(),product.getId()))));
            model.addAttribute("products",products);
            model.addAttribute("productItems", order.getProductList());
            model.addAttribute("cartPage", true);

        }
        return "buyer/cart";
    }

    @DeleteMapping(value= "/products/deleteItems/{product_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addToCart(@PathVariable("product_id") long product_id){
        orderService.deleteItems(product_id);
    }

    @PutMapping(value= "/products/editcart/{product_id}/{selected}")
    public @ResponseBody int editcart(@PathVariable("product_id") long product_id, @PathVariable("selected") int quantity){
        //test user
        User buyer = userService.findById(1L);
        if(buyer==null)buyer = new User(); userService.save(buyer);

        orderService.deleteItems(product_id);
        orderService.addToCart(product_id,quantity, buyer);

        //temp response
        return quantity;
    }

    @GetMapping("/products/checkout")
    public String checkout(Model model){
        Order order = orderService.findById(1L);
        if(order!=null){
            List<Product> products = order.getProductList().stream()
                    .map(productItem -> productItem.getProduct()).distinct().collect(Collectors.toList());
            products.stream().forEach(product -> product.setOrderedAmount(Math.toIntExact(orderService.getProductAmmount(order.getId(),product.getId()))));

            model.addAttribute("order", order);
            model.addAttribute("productItems", order.getProductList());
            model.addAttribute("products",products);

        }

        return "buyer/checkout";
    }

}
