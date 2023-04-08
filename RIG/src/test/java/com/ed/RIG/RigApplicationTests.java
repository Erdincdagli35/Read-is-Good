package com.ed.RIG;

import com.ed.RIG.controller.CustomerController;
import com.ed.RIG.controller.OrderController;
import com.ed.RIG.model.Customer;
import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.service.CustomerService;
import com.ed.RIG.service.OrderService;
import com.ed.RIG.validation.CustomerValidation;
import com.ed.RIG.validation.OrderValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class RigApplicationTests {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;


    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    //Customer - create
    @Test
    void testCreateCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(15L);
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        customer.setUserName("john");
        customer.setPassword("123");

        CustomerValidation customerValidation = Mockito.mock(CustomerValidation.class);
        CustomerService customerService = Mockito.mock(CustomerService.class);

        Mockito.when(customerValidation.existsCustomer(customer)).thenReturn(true);
        Mockito.when(customerService.create(customer)).thenReturn(customer.getId());

        CustomerController customerController = new CustomerController();

        // Act
        ResponseEntity responseEntity = customerController.create(customer);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());

        // Verify that the customer was created
        Mockito.verify(customerValidation, Mockito.times(1)).existsCustomer(customer);
        Mockito.verify(customerService, Mockito.times(1)).create(customer);
    }

    //Customer - addOrder
    @Test
    void testAddOrder() {
        // Arrange
        Long customerId = 1L;
        OnlineOrder order = new OnlineOrder();
        order.setName("Book Order");
        order.setDetail("Book Order Details");

        List<OnlineOrder> orders = new ArrayList<>();
        orders.add(new OnlineOrder());
        orders.add(new OnlineOrder());

        CustomerValidation customerValidation = Mockito.mock(CustomerValidation.class);
        CustomerService customerService = Mockito.mock(CustomerService.class);

        Mockito.when(customerValidation.existsCustomerById(customerId)).thenReturn(true);
        Mockito.when(customerValidation.existsCustomerRelatedWithOrder(customerId)).thenReturn(true);
        Mockito.when(customerService.addOrder(customerId, orders)).thenReturn(Long.valueOf("Order added successfully."));

        CustomerController customerController = new CustomerController();

        // Act
        ResponseEntity responseEntity = customerController.addOrder(customerId, orders);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Order added successfully.", responseEntity.getBody());

        // Verify that the validation and service methods were called
        Mockito.verify(customerValidation, Mockito.times(1)).existsCustomerById(customerId);
        Mockito.verify(customerValidation, Mockito.times(1)).existsCustomerRelatedWithOrder(customerId);
        Mockito.verify(customerService, Mockito.times(1)).addOrder(customerId, orders);
    }

    @Test
    public void testGetAll() {
        // Mock customer list
        Customer customer = new Customer();
        customer.setId(15L);
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        customer.setUserName("john");
        customer.setPassword("123");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        // Mock Page object
        Page<Customer> page = new PageImpl<>(customers);

        // Mock customerService behavior
        when(customerService.getAll(anyInt(), anyInt())).thenReturn(page);

        // Call API endpoint
        ResponseEntity<Page<Customer>> responseEntity = customerController.getAll(1, 10);

        // Check response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check response body
        Page<Customer> responseBody = responseEntity.getBody();
        assertEquals(page, responseBody);
    }

    @Test
    public void testGetById() {
        // Mock customer object
        Customer customer = new Customer();
        customer.setId(15L);
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        customer.setUserName("john");
        customer.setPassword("123");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        // Mock customerService behavior
        when(customerService.getById(15L)).thenReturn(customer);

        // Call API endpoint
        ResponseEntity responseEntity = customerController.getById(15L);

        // Check response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Check response body
        Customer responseBody = (Customer) responseEntity.getBody();
        assertEquals(customer, responseBody);
    }

    @Test
    public void createOrderTest() {
        // Given
        OnlineOrder order = new OnlineOrder();
        List<Long> bookIds = Arrays.asList(1L, 2L, 3L);
        List<Integer> pieces = Arrays.asList(1, 2, 3);

        OrderValidation orderValidation = new OrderValidation();
        when(orderValidation.existsOrder(any(OnlineOrder.class))).thenReturn(false);
        when(orderValidation.existsSameSizeForPageNoAndPageSize(bookIds.size(), pieces.size())).thenReturn(true);
        when(orderService.create(any(OnlineOrder.class), any(List.class), any(List.class)))
                .thenReturn(order.getId());

        // When
        ResponseEntity response = orderController.create(order, bookIds, pieces);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

	@Test
	public void getAllOrdersTest() {
		// Given
		Integer pageNo = 1;
		Integer pageSize = 10;
		Long customerId = 1L;
		OnlineOrder order = new OnlineOrder();
		List<OnlineOrder> orders = Arrays.asList(order);
		Page<OnlineOrder> page = new PageImpl<>(orders);

		when(orderService.getAll(pageNo, pageSize, customerId)).thenReturn(page);

		// When
		ResponseEntity<Page<OnlineOrder>> response = orderController.getAll(pageNo, pageSize, customerId);

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(page, response.getBody());
	}

	@Test
	public void getAllOrdersByStartDateBetweenEndDateTest() {
		// Given
		Integer pageNo = 1;
		Integer pageSize = 10;
		Instant startDate = Instant.now();
		Instant endDate = Instant.now();
		OnlineOrder order = new OnlineOrder();
		List<OnlineOrder> orders = Arrays.asList(order);
		Page<OnlineOrder> page = new PageImpl<>(orders);

		when(orderService.getAllByStartDateBetweenEndDate(pageNo, pageSize, startDate, endDate)).thenReturn(page);

		// When
		ResponseEntity<Page<OnlineOrder>> response = orderController.getAllByStartDateBetweenEndDate(pageNo, pageSize, startDate, endDate);

		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(page, response.getBody());
	}

	@Test
	void getOrderByIdTest() {
		// Arrange
		Long orderId = 1L;
		OnlineOrder expectedOrder = new OnlineOrder();
		expectedOrder.setId(orderId);
		// mock orderService to return the expected order
		when(orderService.getById(orderId)).thenReturn(expectedOrder);

		// Act
		ResponseEntity response = orderController.getById(orderId);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOrder, response.getBody());
	}
}
