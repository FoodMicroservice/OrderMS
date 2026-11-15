
package org.codedecode.orderms.service;

import org.codedecode.orderms.dto.*;
import org.codedecode.orderms.entity.Order;
import org.codedecode.orderms.repository.OrderRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private SequenceGenrator sequenceGenrator;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;


    @Test
    void testSaveOrder() {

        // Arrange

        FoodItemsDTO foodItem = new FoodItemsDTO(
                1,
                "Paneer Tikka",
                true,
                new BigDecimal("250.00"),
                10,
                2
        );

        Restaurant restaurant = new Restaurant(
                10,
                "Food Palace",
                "MG Road",
                "Pune",
                "Family Restaurant"
        );

        OrderDTOfromFrontend inputOrder =
                new OrderDTOfromFrontend(
                        List.of(foodItem),
                        101,
                        restaurant
                );

        UserDTO user = new UserDTO(
                101,
                "Aniket",
                "password",
                "Pune",
                "Pune"
        );

        // Mock sequence generator
        when(sequenceGenrator.generateNextOrder())
                .thenReturn(1);

        // Mock UserInfo microservice response
        String url = "http://userInfo/user/fetchUserById/101";

        when(restTemplate.getForObject(url, UserDTO.class))
                .thenReturn(user);

        // Act

        OrderDTO result = orderService.saveOrder(inputOrder);

        // Assert

        assertNotNull(result);

        assertEquals(1, result.getOrderId());

        assertEquals("Aniket",
                result.getUserDTO().getUserName());

        assertEquals("Food Palace",
                result.getRestaurant().getName());

        assertEquals(1, result.getFoodItems().size());

        assertEquals("Paneer Tikka",
                result.getFoodItems().get(0).getItemName());

        // Verify dependency calls

        verify(sequenceGenrator, times(1))
                .generateNextOrder();

        verify(restTemplate, times(1))
                .getForObject(url, UserDTO.class);

        verify(orderRepo, times(1))
                .save(any(Order.class));
    }
}
