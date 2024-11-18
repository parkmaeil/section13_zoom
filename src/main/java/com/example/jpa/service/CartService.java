package com.example.jpa.service;

import com.example.jpa.entity.Cart;
import com.example.jpa.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getCartItemsByCustomerId(Long customerId) {
        // Retrieve cart items by customer ID from the repository
        return cartRepository.findByCustomer_id(customerId);
    }

    public double getTotalAmount(List<Cart> cartItems) {
        double totalAmount = 0.0;
        for (Cart cartItem : cartItems) {
            // Calculate the total price for each item and add it to the total amount
            totalAmount += cartItem.getBook().getPrice() * cartItem.getQuantity();
        }
        return totalAmount;
    }

}
