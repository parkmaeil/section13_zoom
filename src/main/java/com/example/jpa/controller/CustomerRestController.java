package com.example.jpa.controller;

import com.example.jpa.entity.Customer;
import com.example.jpa.repository.CustomerQueryDSLRepository;
import com.example.jpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

    @Autowired
    private CustomerQueryDSLRepository queryDSLRepository;
    @Autowired
    private CustomerRepository repository;
    // http://localhost:8081/jpa/api/customer/30
    //@GetMapping("/customer/{age}")
    public ResponseEntity<?> getByAge(@PathVariable  int age){
          List<Customer> cus=repository.findByAge(age);
          return new ResponseEntity<>(cus, HttpStatus.OK);
    }
    @PostMapping("/customer")
    public ResponseEntity<?> getUsernameAndPassword(@RequestBody Customer customer ){
        Customer cus=queryDSLRepository.getUsernameAndPassword(customer.getUsername(), customer.getPassword());
        return new ResponseEntity<>(cus, HttpStatus.OK);
    }

    @GetMapping("/customer/user/{username}")
    public ResponseEntity<?> getUsername(@PathVariable String username){
          Customer cus=repository.findByUsernameIs(username);
          return new ResponseEntity<>(cus, HttpStatus.OK);
    }

    //@GetMapping("/customer/{age}") // >=30
    public ResponseEntity<?> findByAgeGreaterThanEqual(@PathVariable  int age){
           List<Customer> clist=repository.findByAgeGreaterThanEqual(age);
           return new ResponseEntity<>(clist, HttpStatus.OK);
    }

    @GetMapping("/customer/{rating}")
    public ResponseEntity<?> getRating(@PathVariable String rating){
          List<Customer> clist=queryDSLRepository.getRating(rating);
          return new ResponseEntity<>(clist, HttpStatus.OK);
    }
}
