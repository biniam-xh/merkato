package edu.mum.mercato.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.domain.*;
import edu.mum.mercato.domain.view_models.CartItem;
import edu.mum.mercato.domain.view_models.CartModalView;
import edu.mum.mercato.domain.view_models.ChargeRequest;
import edu.mum.mercato.domain.view_models.OrderViewModel;
import edu.mum.mercato.service.OrderService;
import edu.mum.mercato.service.ProductService;
import edu.mum.mercato.service.UserService;
import edu.mum.mercato.serviceImpl.ProductServiceImpl;
import edu.mum.mercato.serviceImpl.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.AuthenticationException;
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
    private StripeService paymentsService;


    @Autowired
    UserService userService;
    @GetMapping("/products")
    public String productListing(Model model){
        model.addAttribute("products", productService.getAllProducts() );
        Order order = orderService.getCart(1L);
        if(order!=null){
            model.addAttribute("productItems", order.getProductList());
            System.out.println("");
        }

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
        Order order = orderService.getCart(1L);
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

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;


    @GetMapping("/products/checkout")
    public String checkout(Model model){
        Order order = orderService.getCart(1L);
        if(order!=null){
            List<Product> products = order.getProductList().stream()
                    .map(productItem -> productItem.getProduct()).distinct().collect(Collectors.toList());
            products.stream().forEach(product -> product.setOrderedAmount(Math.toIntExact(orderService.getProductAmmount(order.getId(),product.getId()))));

            model.addAttribute("order", order);
            model.addAttribute("productItems", order.getProductList());
            model.addAttribute("products",products);

            model.addAttribute("amount", (int)(order.getTotalPrice() - order.getDiscount()) * 100); // in cents
            model.addAttribute("stripePublicKey", stripePublicKey);
            model.addAttribute("currency", ChargeRequest.Currency.USD);


        }

        return "buyer/checkout";
    }

    @GetMapping("/checkout/billing")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void makePayment(@RequestParam("billingAddress") Address billingAddress,@RequestParam("orderId") Long orderId, Model model){
        orderService.findById(orderId).setBillingAddress(billingAddress);
        System.out.println("address");
    }

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, @RequestParam("orderId") Long orderId, RedirectAttributes rd, Model model)
            throws StripeException, AuthenticationException {
        chargeRequest.setDescription("Payment charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        Charge charge = paymentsService.charge(chargeRequest);
        rd.addFlashAttribute("id", charge.getId());
        rd.addFlashAttribute("status", charge.getStatus());
        rd.addFlashAttribute("chargeId", charge.getId());
        rd.addFlashAttribute("balance_transaction", charge.getBalanceTransaction());

        orderService.changeStatus(orderId, OrderStatus.ORDERED);
        Order order = orderService.findById(orderId);

        Payment payment = new Payment(charge.getStatus(),charge.getId(),charge.getBalanceTransaction());

        order.setPayment(payment);
        orderService.savePayment(payment);

        return "redirect:/orderConfirmation";
    }

    @GetMapping("/orderConfirmation")
    public String orderConfirmation(){
        return "buyer/order_confirmation";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orderViewModels",getViewModels(true));
        model.addAttribute("isCurrent", true);
        return "buyer/orders";
    }

    @GetMapping("/orderHistory")
    public String orderHistory(Model model){
        model.addAttribute("orderViewModels",getViewModels(false));
        model.addAttribute("isCurrent", false);
        return "buyer/orders";
    }

    public List<OrderViewModel> getViewModels(boolean isCurrent){
        List<Order> orders = new ArrayList<>();
        List<OrderViewModel> orderViewModels = new ArrayList<>();
        if(isCurrent){
            orders = orderService.getActiveOrders(1L,false);
        }
        else{
            orders = orderService.getNonActiveOrders(1L,false);
        }
        for(Order order: orders){
            List<Product> products = order.getProductList().stream()
                    .map(productItem -> productItem.getProduct()).distinct().collect(Collectors.toList());
            products.stream().forEach(product -> product.setOrderedAmount(Math.toIntExact(orderService.getProductAmmount(order.getId(),product.getId()))));
            orderViewModels.add(new OrderViewModel(order,products));
        }
        return orderViewModels;
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }

    @GetMapping(value= "/products/user/follow/{user_id}")
    public @ResponseBody int follow(@PathVariable("user_id") long user_id){
        //test user
        User currentUser = userService.findById(user_id);
        User user = userService.findById(user_id);
        user.getFollowers().add(currentUser);
        userService.save(user);

        //temp response
        return 1;
    }

    @GetMapping(value= "/products/user/unfollow/{user_id}")
    public @ResponseBody int unfollow(@PathVariable("user_id") long user_id){
        //test user
        User currentUser = userService.findById(user_id);
        User user = userService.findById(user_id);
        user.getFollowers().remove(currentUser);
        userService.save(user);

        //temp response
        return 1;
    }

    @GetMapping("/orders/cancelOrder")
    public String cancelOrder(@RequestParam("orderId") Long orderId, Model model){
        orderService.changeStatus(orderId, OrderStatus.CANCELED);
        return "redirect:/orderHistory";
    }

}
