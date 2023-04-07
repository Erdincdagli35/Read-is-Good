package com.ed.RIG.service;

import com.ed.RIG.model.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<Book> getAll(Integer pageNo, Integer pageSize);

    Book getById(Long id);

    Long create(Book book);

    Book editStock(Long bookId, Integer stock);
}
