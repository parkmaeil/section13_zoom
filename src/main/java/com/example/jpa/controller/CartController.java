package com.example.jpa.controller;

import com.example.jpa.entity.Cart;
import com.example.jpa.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/cart/delete/{id}")
    public String cartDelete(@PathVariable Long id){
        Cart cart=cartService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found")); ;
        cartService.cartDelete(cart);
        // cart.getCustomer().getId()
        //return "redirect:/books";
        return "redirect:/cart/list?customerId="+cart.getCustomer().getId();
    }

    @GetMapping("/cart/edit/{id}/{quantity}")
    public String quantityUpdate(@PathVariable Long id, @PathVariable int quantity){
        Cart cart=cartService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found")); ;
        cart.setQuantity(quantity);
        cartService.suUpdate(cart); // 수정
        return "redirect:/cart/list?customerId="+cart.getCustomer().getId();
    }
}
