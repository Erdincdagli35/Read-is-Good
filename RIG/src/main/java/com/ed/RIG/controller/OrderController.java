package com.ed.RIG.controller;

import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.repository.CustomerRepository;
import com.ed.RIG.repository.OrderRepository;
import com.ed.RIG.service.OrderService;
import com.ed.RIG.validation.OrderValidation;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/*
 * I created the Controller for Book
 * APIs :
 *   create POST
 *   getAll GET
 *   byDate GET
 *   getById GET
 *
 * The Controller provides a basic create, getAll, getById API.
 * Also, I created a byDate API for OnlineOrder - Book Entity Relationship
 * */
@RestController
@RequestMapping("/orders")
@EnableAutoConfiguration
@CrossOrigin
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderValidation orderValidation;

    /*Placing a new order*/
    //I create an order as basic. There is a basic existsCustomer validation for creating with same id.
    //Also, There are two critical validation for pieces, bookId size check and book relationship check
    //The API returns the id as response. If we don't enter booksId and pieces. The API create just an order.
    //Otherwise, the API create an order and add the books.
    @PostMapping
    public ResponseEntity create(@RequestBody OnlineOrder order,
                                 @RequestParam(value = "bookIds", required = false) List<Long> bookIds,
                                 @RequestParam(value = "pieces", required = false) List<Integer> pieces)  {
        if (!orderValidation.existsOrder(order)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is a order with same id:" + order.getId());
        }
        if (!orderValidation.existsSameSizeForPageNoAndPageSize(bookIds.size(), pieces.size())) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Book size and pieces size must be same");
        }
        /*
        if (!orderValidation.existsOrderRelatedWithBook(order, bookIds)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is a order with same id:" + order.getId());
        }*/

        return ResponseEntity.ok(orderService.create(order, bookIds, pieces));
    }

    //I list all orders this API as pageable. There are two parameters for pageable.
    //The parameters are mandatory. The API returns all orders data as pageable
    //Please use the 1-10 for pageNo. It's starting 1.
    @GetMapping
    public ResponseEntity<Page<OnlineOrder>> getAll(@RequestParam(value = "pageNo") Integer pageNo,
                                                    @RequestParam(value = "pageSize") Integer pageSize,
                                                    @RequestParam(value = "customerId", required = false) Long customerId) {
        return ResponseEntity.ok(orderService.getAll(pageNo,pageSize,customerId));
    }

    /*List orders by date interval ( startDate - endDate )*/
    //I list all orders by startDate and endDate as pageable. There are two parameters for pageable.
    //The parameters are mandatory. The API returns all orders data as pageable
    //Please use the 1-10 for pageNo. It's starting 1.
    @GetMapping("/byDate")
    public ResponseEntity<Page<OnlineOrder>> getAllByStartDateBetweenEndDate(@RequestParam(value = "pageNo") Integer pageNo,
                                                                             @RequestParam(value = "pageSize") Integer pageSize,
                                                                             @RequestParam(value = "startDate", required = false) Instant startDate,
                                                                             @RequestParam(value = "endDate", required = false) Instant endDate ) {
        return ResponseEntity.ok(orderService.getAllByStartDateBetweenEndDate(pageNo,pageSize,startDate,endDate));
    }

    /*Viewing the order details*/
    //I list an order by order id. The API returns a order.
    //There is a basic existOrder validation.
    @GetMapping("/{orderId}")
    public ResponseEntity getById(@PathVariable Long orderId) {
        if (!orderValidation.existsOrderById(orderId)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is not a order id : " + orderId);
        }

        return ResponseEntity.ok(orderService.getById(orderId));
    }
}
