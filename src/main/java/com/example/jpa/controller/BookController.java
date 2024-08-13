package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    // http://localhost:8081/jpa/books
    @GetMapping("/books")
    public String getAllBooks(Model model){
         List<Book> books=bookService.getAllBooks();
         model.addAttribute("books", books);
         return "bookList"; // bookList.html
    }
}
