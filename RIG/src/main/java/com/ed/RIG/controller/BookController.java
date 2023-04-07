package com.ed.RIG.controller;

import com.ed.RIG.model.Book;
import com.ed.RIG.service.BookService;
import com.ed.RIG.validation.BookValidation;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * I created the Controller for Book
 * APIs :
 *   create POST
 *   getAll GET
 *   updateStock PUT
 *   getById GET
 *
 * The Controller provides a basic create, getAll, getById API.
 * Also, I created a updateStock API.
 * */
@RestController
@RequestMapping("/books")
@EnableAutoConfiguration
@CrossOrigin
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookValidation bookValidation;

    /*Will persist new book*/
    //I create a book as basic. There is a basic existsBook validation for creating with same id.
    //The API returns the id as response
    @PostMapping
    public ResponseEntity create(@RequestBody Book book) {
        if (!bookValidation.existsBook(book)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is a book with same id:" + book.getId());
        }

        return ResponseEntity.ok(bookService.create(book));
    }

    /*Tracking the stock of books*/
    //I list all books this API as pageable. There are two parameters for pageable.
    //The parameters are mandatory. The API returns all books data as pageable
    //Please use the 1-10 for pageNo. It's starting 1.
    @GetMapping
    public ResponseEntity<Page<Book>> getAll(@RequestParam(value = "pageNo") Integer pageNo,
                                             @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(bookService.getAll(pageNo, pageSize));
    }

    /*Will update book’s stock*/
    //I edited just stock with PUT. There is a basic existsBookById validation.
    //The API the edited book data.
    @PutMapping("/{bookId}/updateStock")
    public ResponseEntity edit(@PathVariable Long bookId,
                               @RequestBody Integer stock) {
        if (!bookValidation.existsBookById(bookId)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is not a book id : " + bookId);
        }

        return ResponseEntity.ok(bookService.editStock(bookId, stock));
    }

    //I list a customer by book id. The API returns a book.
    //There is a basic existsBookById validation.
    @GetMapping("/{bookId}")
    public ResponseEntity getById(@PathVariable Long bookId) {
        if (!bookValidation.existsBookById(bookId)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is not a book id : " + bookId);
        }

        return ResponseEntity.ok(bookService.getById(bookId));
    }
}
