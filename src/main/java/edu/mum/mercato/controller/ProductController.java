package edu.mum.mercato.controller;

import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductImage;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String displaySellerPanel(@ModelAttribute("newProduct") Product product, Model model){


        return "product/addProductForm";
    }


    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("newProduct") Product product, BindingResult bindingResult,
                             HttpServletRequest request) throws IOException {

        String rootDirectory= request.getSession().getServletContext().getRealPath("/");
        ProductImage imOb = new ProductImage();
        imOb.setImageURL(rootDirectory);
        product.setImages(imOb);

        productService.saveProduct(product);

        return "redirect:/list";
    }

    @RequestMapping("/list")
    public String displayList(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "product/productList";
    }

//    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
//    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
//                             @RequestParam("id") long id) throws IOException {
//        Product product = null;
//            product = (Product) productService.getProductById(id);
//
//        if (product != null && product.getImages().get(0) != null) {
//            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//            response.getOutputStream().write(product.getImages().get(0).getImage());
//        }
//        response.getOutputStream().close();
//    }

}
