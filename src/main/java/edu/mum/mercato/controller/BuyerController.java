package edu.mum.mercato.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import edu.mum.mercato.Helper.OrderStatus;
import edu.mum.mercato.Helper.ReviewStatus;
import edu.mum.mercato.config.MerkatoUserDetails;
import edu.mum.mercato.domain.*;
import edu.mum.mercato.domain.view_models.CartItem;
import edu.mum.mercato.domain.view_models.CartModalView;
import edu.mum.mercato.domain.view_models.ChargeRequest;
import edu.mum.mercato.domain.view_models.OrderViewModel;

import edu.mum.mercato.service.*;

import edu.mum.mercato.serviceImpl.ProductServiceImpl;
import edu.mum.mercato.serviceImpl.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    SecurityService securityService;

    @Autowired
    AdvertService advertService;
    @Autowired
    private StripeService paymentsService;

    @Autowired
    CouponService couponService;


    @ModelAttribute("user")
    public MerkatoUserDetails getDetails(){
        return securityService.findLoggedInUser();
    }


    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;


    @GetMapping("/products")
    public String productListing(Model model){
        model.addAttribute("products", productService.getAllProducts() );

        MerkatoUserDetails userDetails = securityService.findLoggedInUser();

        if(userDetails!=null){
            Order order = orderService.getCart(userDetails.getId());
            if(order!=null){
                model.addAttribute("productItems", order.getProductList());
                System.out.println("");
            }
        }

        Advert advert=advertService.findOneAdvert();
        System.out.println(advert);
        model.addAttribute("advert",advert);

        if(securityService.findLoggedInUser()!=null){
            model.addAttribute("currentUser",userService.findById(securityService.findLoggedInUser().getId()));
        }


        return "buyer/product_list";
    }
    @PostMapping("/products/addtocart")
    public @ResponseBody int addToCart(@RequestBody CartItem item){
          //test user

        MerkatoUserDetails userDetails = securityService.findLoggedInUser();

          User buyer = userService.findById(userDetails.getId());
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
        MerkatoUserDetails userDetails = securityService.findLoggedInUser();
        Order order = orderService.getCart(userDetails.getId());
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
        MerkatoUserDetails userDetails = securityService.findLoggedInUser();
        User buyer = userService.findById(userDetails.getId());
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
        MerkatoUserDetails userDetails = securityService.findLoggedInUser();
        Order order = orderService.getCart(userDetails.getId());
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

    @GetMapping("/products/checkout/billing")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateOrderAddresses(@RequestParam Address billingAddress, @RequestParam Address shippingAddress, @RequestParam Long orderId, Model model){
        Order order = orderService.findById(orderId);
        order.setBillingAddress(billingAddress);
        order.setBillingAddress(shippingAddress);

        orderService.saveOrder(order);

        System.out.println(billingAddress);
        System.out.println(shippingAddress);
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

        //update coupon
        Coupon coupon = couponService.getCoupon(order.getBuyer().getId());
        coupon.setPoint(coupon.getPoint() + (100 * order.getProductList().size()));
        couponService.save(coupon);

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

        MerkatoUserDetails userDetails = securityService.findLoggedInUser();

        boolean isSeller = false;
        if(userDetails.getRole().getId() == 2){
            isSeller = true;
        }

        if(isCurrent){
            orders = orderService.getActiveOrders(userDetails.getId(),isSeller);
        }
        else{
            orders = orderService.getNonActiveOrders(userDetails.getId(),isSeller);
        }
        for(Order order: orders){
            List<Product> products = order.getProductList().stream()
                    .map(productItem -> productItem.getProduct()).distinct().collect(Collectors.toList());
            products.stream().forEach(product -> product.setOrderedAmount(Math.toIntExact(orderService.getProductAmmount(order.getId(),product.getId()))));
            Coupon coupon = couponService.getCoupon(order.getBuyer().getId());
            orderViewModels.add(new OrderViewModel(order,products,coupon,isSeller));
        }
        return orderViewModels;
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }

    @PutMapping(value= "/products/user/follow/{user_id}")
    public @ResponseBody int follow(@PathVariable("user_id") long user_id){

        MerkatoUserDetails userDetails = securityService.findLoggedInUser();
        User currentUser = userService.findById(userDetails.getId());
        User user = userService.findById(user_id);
        user.getFollowers().add(currentUser);
        userService.save(user);

        //temp response
        return 1;
    }

    @PutMapping(value= "/products/user/unfollow/{user_id}")
    public @ResponseBody int unfollow(@PathVariable("user_id") long user_id){
        MerkatoUserDetails userDetails = securityService.findLoggedInUser();
        User currentUser = userService.findById(userDetails.getId());
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

    @GetMapping("/products/review/{productId}")
    public String getReviews(@PathVariable("productId") Long productId, Model model){
        Review review = new Review();
        Product product = productService.getProductById(productId);
        if(product!=null){
            review.setProduct(product);
            review.setFullName("test user");
        }
        model.addAttribute("review", review);
        model.addAttribute("reviewList", reviewService.getProductReviews(productId, ReviewStatus.APPROVED));
        return "buyer/reviews";
    }

    @PostMapping("/products/review/add")
    public String addReviewForm(@ModelAttribute("review") Review review){
            review.setProduct(productService.getProductById(review.getProduct().getId()));
            reviewService.save(review);
            return "redirect:/products/review/"+review.getProduct().getId();
    }

    @GetMapping("/orders/changeStatus")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void changeOrderStatus(@RequestParam int status, @RequestParam Long orderId, Model model){
        switch (status){
            case 1:
                orderService.changeStatus(orderId,OrderStatus.ORDERED);
                break;
            case 2:
                orderService.changeStatus(orderId,OrderStatus.SHIPPED);
                break;
            case 3:
                orderService.changeStatus(orderId,OrderStatus.DELIVERED);
                break;
        }

    }

}
