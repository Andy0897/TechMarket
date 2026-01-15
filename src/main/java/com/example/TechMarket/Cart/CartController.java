package com.example.TechMarket.Cart;

import com.example.TechMarket.User.User;
import com.example.TechMarket.User.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/carts")
public class CartController {
    CartService cartService;
    UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping("/my-cart")
    public String getShowCart(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        Cart cart = user.getCart();
        model.addAttribute("cart", cart);
        return "cart/my-cart";
    }

    @PostMapping("/submit-add-product/{productId}")
    public String getSubmitAddItem(@PathVariable("productId") Long productId, Principal principal) {
        return cartService.submitAddProduct(productId, principal);
    }

    @PostMapping("/submit-remove-product/{productId}")
    public String getSubmitRemoveItem(@PathVariable("productId") Long productId, Principal principal) {
        return cartService.submitRemoveProduct(productId, principal);
    }
}