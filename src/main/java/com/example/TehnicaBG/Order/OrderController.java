package com.example.TehnicaBG.Order;

import com.example.TehnicaBG.OrderStatus.OrderStatus;
import com.example.TehnicaBG.User.User;
import com.example.TehnicaBG.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    OrderService orderService;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository, UserRepository userRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getShowOrders(Model model) {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        model.addAttribute("orders", orders.reversed());
        return "order/show-all";
    }

    @GetMapping("/{orderId}")
    public String getShowOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).get();
        model.addAttribute("order", order);
        return "order/show-single";
    }

    @GetMapping("/purchase")
    public String getPurchaseOrder(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "order/purchase";
    }

    @PostMapping("/submit-purchase")
    public String getSubmitPurchase(@Valid Order order, BindingResult bindingResult, Principal principal, Model model) {
        return orderService.submitPurchase(order, bindingResult, principal, model);
    }

    @PostMapping("/submit-change-order-status/{orderId}")
    public String getChangeOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam(name = "orderStatus") OrderStatus orderStatus) {
        return orderService.submitChangeOrderStatus(orderId, orderStatus);
    }

    @GetMapping("/my-orders")
    public String getShowMyOrders(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("orders", orderRepository.findAllByBuyerId(user.getId()));
        return "order/my-orders";
    }
}