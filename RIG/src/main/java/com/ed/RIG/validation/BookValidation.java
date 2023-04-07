package com.ed.RIG.validation;

import com.ed.RIG.model.Book;
import com.ed.RIG.model.Customer;
import com.ed.RIG.repository.BookRepository;
import com.ed.RIG.repository.CustomerRepository;
import com.ed.RIG.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidation {

    @Autowired
    BookRepository bookRepository;

    public boolean existsBook(Book book) {
        return bookRepository.findOneById(book.getId()) == null;
    }

    public boolean existsBookById(Long id) {
        return bookRepository.findOneById(id) != null;
    }
}
