package com.example.TechMarket.Cart;

import com.example.TechMarket.Product.Product;
import com.example.TechMarket.Product.ProductRepository;
import com.example.TechMarket.User.User;
import com.example.TechMarket.User.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CartService {
    CartRepository cartRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String submitAddProduct(Long productId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Product product = productRepository.findById(productId).get();
        product.setAvailable(false);
        productRepository.save(product);
        Cart cart = user.getCart();
        cart.addProduct(product);
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        cartRepository.save(cart);
        return "redirect:/carts/my-cart";
    }

    public String submitRemoveProduct(Long productId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Product product = productRepository.findById(productId).get();
        product.setAvailable(true);
        productRepository.save(product);
        Cart cart = user.getCart();
        cart.removeProduct(product);
        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
        cartRepository.save(cart);
        return "redirect:/carts/my-cart";
    }
}