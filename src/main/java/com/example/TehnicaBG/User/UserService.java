package com.example.TehnicaBG.User;

import com.example.TehnicaBG.Cart.Cart;
import com.example.TehnicaBG.Cart.CartRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class UserService {
    BCryptPasswordEncoder encoder;
    UserRepository userRepository;
    CartRepository cartRepository;

    public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository, CartRepository cartRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public String submitUser(User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors() || checkIfExistsUserByUsername(user.getUsername()) || checkIfExistsUserByEmail(user.getEmail())) {
            model.addAttribute("user", user);
            model.addAttribute("existsUserByUsername", checkIfExistsUserByUsername(user.getUsername()));
            model.addAttribute("existsUserByEmail", checkIfExistsUserByEmail(user.getEmail()));
            return "sign-up";
        }
        user.setEnable(true);
        user.setRole("USER");
        user.setPassword(encoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return "redirect:/sign-in";
    }

    public String blockUser(Long userId) {
        User user = userRepository.findById(userId).get();
        user.setEnable(false);
        userRepository.save(user);
        return "redirect:/";
    }

    public boolean checkIfExistsUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }

    public boolean checkIfExistsUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }
}