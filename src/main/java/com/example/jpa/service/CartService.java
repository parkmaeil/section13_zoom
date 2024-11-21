package com.example.jpa.service;

import com.example.jpa.entity.Book;
import com.example.jpa.entity.Cart;
import com.example.jpa.entity.Customer;
import com.example.jpa.repository.BookRepository;
import com.example.jpa.repository.CartRepository;
import com.example.jpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    public void addToCart(Long bookId, Long customerId) {
        // Retrieve the Book and Customer objects based on their IDs
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found")); // Handle if book is not found
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found")); // Handle if customer is not found

        Cart dbCart=cartRepository.findByCustomerAndBook(customerId, bookId);
        if(dbCart!=null) {
             System.out.println(dbCart.getId());
             // 업데이트....
            dbCart.setQuantity(dbCart.getQuantity()+1);
            cartRepository.save(dbCart); // update
        }else{
            // 담기
            Cart cart = new Cart();
            cart.setBook(book); // Set the Book object
            cart.setCustomer(customer); // Set the Customer object
            cart.setQuantity(1); // Assuming the default quantity is 1
            cart.setCartDate(new Date()); // Set the current date
            cartRepository.save(cart); // insert
        }
    }
}
