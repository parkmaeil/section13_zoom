package com.example.jpa.service;

import com.example.jpa.entity.Customer;
import com.example.jpa.entity.Review;
import com.example.jpa.repository.CustomerRepository;
import jakarta.persistence.FetchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private  CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        // 고객전체 리스트 가져오기
        /*
        Customer : user1  , EAGER - reviews(3) -  JOIN
        Customer : user2,  EAGER - reviews(3) -  JOIN
        Customer : user3,  EAGER - reviews(3) - JOIN
         */
         /*
        Customer : user1  , LAZY - reviews(x) -
        Customer : user2,  LAZY - reviews(x) -
        Customer : user3,  LAZY - reviews(x) -
         */
        List<Customer> customers=customerRepository.findAll(); // select SQL~ Customer 정보
        //for(Customer cus : customers){ //
        //    System.out.println(cus.getReviews().size()); // SQL쿼리 트리거(이벤트)  : id (1)<--JOIN->Review(customer_id(1))
        //}
        return customers;
    }

    @Transactional
    public List<Review> getReviewsByCustomerId(Long id){
          Customer customer=customerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Customer not found")); // SQL
          return customer.getReviews(); // SQL : customer(id : 1)<--JOIN-->Review(customer_id(3))
    }
}
