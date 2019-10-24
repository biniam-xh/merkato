package edu.mum.mercato.controller;

import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductImage;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody User addUser(@Valid @RequestBody User user){

        return userService.save(user);
    }

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public String displaySellerPanel(@ModelAttribute("product") Product product){

        return "seller/seller";
    }
}
