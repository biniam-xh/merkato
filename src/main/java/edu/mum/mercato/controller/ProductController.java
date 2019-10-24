package edu.mum.mercato.controller;

import edu.mum.mercato.config.MerkatoUserDetails;
import edu.mum.mercato.config.productEvents.ProductAddEvent;
import edu.mum.mercato.domain.Category;
import edu.mum.mercato.domain.Product;
import edu.mum.mercato.domain.ProductImage;
import edu.mum.mercato.domain.User;
import edu.mum.mercato.service.ProductService;
import edu.mum.mercato.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String displaySellerPanel(@ModelAttribute("newProduct") Product product, Model model){

        Category category = new Category();
        product.setCategory(category);

        return "product/addProductForm";
    }


    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("newProduct") Product product, BindingResult bindingResult,
                             HttpServletRequest request) throws IOException {

        Product product1 = productService.getByProductByTitleAndCategory(product.getTitle(), product.getCategory().getCategoryName());
        if (product1 != null){
//            product1.setNumberOfCopies(product1.getNumberOfCopies() + product.getNumberOfCopies());
            product.setNumberOfCopies(product1.getNumberOfCopies() + product.getNumberOfCopies());
            product.setId(product1.getId());
            productService.saveProduct(product);
        } else {
            productService.saveProduct(product);
        }

        return "redirect:/list";
    }

    @RequestMapping("/list")
    public String displayList(Model model){
//        List<Product> products = productService.getAllProducts();


        MerkatoUserDetails mud = securityService.findLoggedInUser();
        String nameOfUser = mud.getFirstName();
        List<Product> products = productService.getAllProductsBySeller(mud.getId());

        model.addAttribute("products", products);
        model.addAttribute("userFirstName", nameOfUser);

        return "product/productList";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {

        productService.deleteProductById(id);

        return "redirect:/list";
    }

    @RequestMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") int id, Model model) {

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "product/editProduct";
    }

    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute("product") Product product, BindingResult bindingResult) throws IOException {

//        productService.deleteProduct(product);
//        productService.deleteProductById(id);
        productService.saveProduct(product);
//        productService.updateProduct(product);

        return "redirect:/list";
    }

    @RequestMapping(value = "/addProductEvent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> someAction() {
//        System.out.println("In Controller 1");

        productService.notify(new ProductAddEvent("hello "), "Seller");
//        System.out.println("In Controller 2");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
