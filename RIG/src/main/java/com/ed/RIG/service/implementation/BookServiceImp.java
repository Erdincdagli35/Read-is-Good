package com.ed.RIG.service.implementation;

import com.ed.RIG.model.Book;
import com.ed.RIG.repository.BookRepository;
import com.ed.RIG.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
@AllArgsConstructor
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Long create(Book book) {
        log.info("Book : create(book)");
        book= bookRepository.save(book);
        return book.getId();
    }

    @Override
    public Book editStock(Long bookId, Integer stock) {
        log.info("Book : editStock(book, stock)");
        Book book = bookRepository.findOneById(bookId);
        book.setStock(stock);
        book=bookRepository.save(book);
        return book;
    }

    @Override
    public Page<Book> getAll(Integer pageNo, Integer pageSize) {
        log.info("Book : getAll(pageNo, pageSize)");
        Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getById(Long id) {
        log.info("Book : getById(id)");
        return bookRepository.findOneById(id);
    }
}