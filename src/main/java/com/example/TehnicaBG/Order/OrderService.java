package com.example.TehnicaBG.Order;

import com.example.TehnicaBG.Cart.Cart;
import com.example.TehnicaBG.Cart.CartRepository;
import com.example.TehnicaBG.OrderStatus.OrderStatus;
import com.example.TehnicaBG.User.User;
import com.example.TehnicaBG.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;

@Service
public class OrderService {
    OrderRepository orderRepository;
    CartRepository cartRepository;
    UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public String submitPurchase(Order order, BindingResult bindingResult, Principal principal, Model model) {
        if(bindingResult.hasFieldErrors("city") || bindingResult.hasFieldErrors("address")) {
            model.addAttribute("order", order);
            return "order/purchase";
        }
        User buyer = userRepository.findByUsername(principal.getName());
        order.setBuyer(buyer);
        Cart cart = buyer.getCart();
        order.setProducts(cart.getProducts());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderStatus(OrderStatus.IN_REVIEW);
        cart.setProducts(new ArrayList<>());
        cart.setTotalPrice(0);
        orderRepository.save(order);
        cartRepository.save(cart);
        return "redirect:/";
    }

    public String submitChangeOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).get();
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return "redirect:/orders/" + orderId;
    }
}
