package com.ed.RIG.service.implementation;

import com.ed.RIG.constant.Month;
import com.ed.RIG.model.Book;
import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.repository.BookRepository;
import com.ed.RIG.repository.OrderRepository;
import com.ed.RIG.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    @Override
    public Page<OnlineOrder> getAll(Integer pageNo, Integer pageSize, Long customerId) {
        log.info("OnlineOrder : getAll()");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return customerId == null ? orderRepository.findAll(pageable) : orderRepository.findAllByCustomer_Id(pageable, customerId);
    }

    @Override
    public OnlineOrder getById(Long id) {
        log.info("Customer : getById(id)");
        return orderRepository.findOneById(id);
    }

    @Override
    public Long create(OnlineOrder order, List<Long> bookIds, List<Integer> pieces) {
        log.info("Customer : create(order, bookIds)");

        //We must enter bookIds and pieces as same size. If we add the book or books
        if (bookIds.size() != 0 && pieces.size() != 0) {
            Set<Book> books = new HashSet<>();
            int index = 0;

            for (Long bookId : bookIds) {
                books.add(bookRepository.findOneById(bookId));
                bookUpdateStock(bookId, pieces.get(index));
                orderDateCastToMonth(order);
                index++;
            }
            order.setBooks(books);
        }

        order = orderRepository.save(order);
        return order.getId();
    }

    private void orderDateCastToMonth(OnlineOrder order) {
        Instant startDateInstant = order.getStartDate();
        ZoneId zone = ZoneId.of("Europe/Istanbul");
        LocalDate startDateLocal = LocalDate.ofInstant(startDateInstant, zone);
        int month = startDateLocal.getMonthValue();
        switch (month) {
            case 1:
                order.setMonth(Month.January);
                break;
            case 2:
                order.setMonth(Month.February);
                break;
            case 3:
                order.setMonth(Month.March);
                break;
            case 4:
                order.setMonth(Month.April);
                break;
            case 5:
                order.setMonth(Month.May);
                break;
            case 6:
                order.setMonth(Month.June);
                break;
            case 7:
                order.setMonth(Month.July);
                break;
            case 8:
                order.setMonth(Month.August);
                break;
            case 9:
                order.setMonth(Month.September);
                break;
            case 10:
                order.setMonth(Month.October);
                break;
            case 11:
                order.setMonth(Month.November);
                break;
            case 12:
                order.setMonth(Month.December);
                break;
            default:
        }


    }

    private void bookUpdateStock(Long bookId, Integer pieces) {
        Book book = bookRepository.findOneById(bookId);
        Integer defaultStock = book.getStock();
        book.setStock(defaultStock - pieces);
        book.setPiece(pieces);
        bookRepository.save(book);
    }

    @Override
    public Page<OnlineOrder> getAllByStartDateBetweenEndDate(Integer pageNo, Integer pageSize,
                                                             Instant startDate, Instant endDate) {
        log.info("Customer : getAllByStartDateBetweenEndDate(pageNo,pageSize,startDate,endDate)");
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return orderRepository.findAllByStartDateBetweenEndTime(startDate, endDate, pageable);
    }
}