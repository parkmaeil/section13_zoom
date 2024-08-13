package com.example.jpa.repository;

import com.example.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 1. JpaRepository 에서 제공되는 메서드를 사용하는 방법

    // 2. 퀴리메서드를 사용하는 방법(규칙을 가지고 만들어지는 메서드)
    // findBy<멤버변수이름> : select * from customer where <멤버변수이름>=?(매개변수)
    public List<Customer> findByAge(int age);  // 30
     // username과  password가 일치하는 고객?
    public Customer findByUsernameAndPassword(String username, String password);
    // select cus from Customer c where c.username=:username and c.password=:password

    public Customer findByUsernameIs(String username);

    public List<Customer> findByAgeGreaterThanEqual(int age);

    // 3. JPQL(사용자 정의 쿼리) : Java Persistence Query Language
    // - 1. Entity(Object)를 기준으로 쿼리 만들기
    // - 2. Table을 기준으로 쿼리 만들기(nativeQuery)
    //@Query("select cus from Customer cus where cus.rating=:rating")
    @Query(value = "select * from customer where rating=?1", nativeQuery = true)
    public List<Customer> getRating(String rating); // vip, gold, silver

    @Query(value = "select * from customer c where c.username=?1 and c.password=?2", nativeQuery = true)
    public Customer getUsernameAndPassword(String username, String password);

    // 4. QueryDSL : Hibernate Query Language의 쿼리를 타입에 안전하게 생성 및 관리해주는 Java 프레임워크이다.
    // Domain Specific Language (DSL) 형식으로 쿼리를 작성할 수 있게 해줍니다.
    // 이는 SQL보다 가독성이 높고, 개발자가 쿼리를 더 직관적이고 유연하게 작성할 수 있도록 도와줍니다.

}
