package com.ed.RIG.validation;

import com.ed.RIG.model.Book;
import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.repository.BookRepository;
import com.ed.RIG.repository.OrderRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class OrderValidation {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BookRepository bookRepository;

    public boolean existsOrder(OnlineOrder order) {
        if (orderRepository.findOneById(order.getId()) == null){
            return true;
        }

        return false;
    }

    public boolean existsOrderById(Long id) {
        return orderRepository.findOneById(id) != null;
    }

    public boolean existsSameSizeForPageNoAndPageSize(Integer bookIdsSize, Integer piecesSize){
        return Objects.equals(bookIdsSize, piecesSize);
    }

    public boolean existsOrderRelatedWithBook(OnlineOrder order, List<Long> bookIds) {
        boolean isExists = false;
        for (Long bookId : bookIds){
            Book book = bookRepository.findOneById(bookId);
            if (order.getBooks().contains(book)){
                isExists = true;
            }
        }

        return  isExists;
    }
}
