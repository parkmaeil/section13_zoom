package com.example.jpa.controller;

import com.example.jpa.entity.Customer;
import com.example.jpa.entity.Review;
import com.example.jpa.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // http://localhost:8081/jpa/customers
    @GetMapping("/customers")
    public String showCustomerList(Model model){
         List<Customer> customers=customerService.getAllCustomers();
         model.addAttribute("customers", customers);
         return "customerList" ;  // customerList.html
    }

    @GetMapping("/customer/reviews")
    public String getReviews(@RequestParam("id") Long id, Model model){
               List<Review> reviews=customerService.getReviewsByCustomerId(id);
               model.addAttribute("reviews", reviews);
               return "reviewList"; //  reviewList.html
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, HttpSession session) {
        Customer customer = customerService.authenticate(username, password);
        if (customer != null) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("customer", customer);
            return "redirect:/books"; // Redirect to the book list page after successful login
        } else {
            return "redirect:/login?error=true"; // Redirect back to the login page with an error flag
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/books";
    }
}
