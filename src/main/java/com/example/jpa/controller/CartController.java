package com.example.jpa.controller;

import com.example.jpa.entity.Cart;
import com.example.jpa.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart/list")
    public String viewCart(@RequestParam("customerId") Long customerId, Model model) {
        // Retrieve cart items for the given customer ID
        List<Cart> cartItems = cartService.getCartItemsByCustomerId(customerId);
        double totalAmount = cartService.getTotalAmount(cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);

        return "cartList"; // Return the cart list view
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("bookId") Long bookId, @RequestParam("customerId") Long customerId) {
        cartService.addToCart(bookId, customerId);
        return "redirect:/books"; // Redirect to the book list page after adding to cart
    }


}
