package com.ed.RIG.repository;

import com.ed.RIG.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Book findOneById(Long id);

    Page<Book> findAll(Pageable pageable);

    @Query("Select Count(b.id) From Book b ")
    Integer findTotalBookCount();
}
