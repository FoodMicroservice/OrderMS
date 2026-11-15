package org.codedecode.orderms.controller;

import org.codedecode.orderms.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import org.codedecode.orderms.dto.FoodItemsDTO;
import org.codedecode.orderms.dto.OrderDTO;
import org.codedecode.orderms.dto.OrderDTOfromFrontend;
import org.codedecode.orderms.dto.Restaurant;
import org.codedecode.orderms.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testSaveOrder(){
        //Arrange
        FoodItemsDTO foodItem = new FoodItemsDTO(1,
                "Paneer Tikka",
                true,
                new BigDecimal("250.00"),
                10,
                2);
        Restaurant restaurant=new Restaurant( 10,
                "Food Palace",
                "MG Road",
                "Pune",
                "Family Restaurant");

        OrderDTOfromFrontend inputOrder=new OrderDTOfromFrontend(
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
        OrderDTO savedOrder = new OrderDTO(
                1,
                List.of(foodItem),
                restaurant,
                user
        );

        when(orderService.saveOrder(inputOrder)).thenReturn(savedOrder);

        //Act
        ResponseEntity<OrderDTO> response = orderController.saveOrder(inputOrder);

        //Assert
        assertEquals(HttpStatus.CREATED,response.getStatusCode())
;       assertNotNull(response.getBody());
        assertEquals(1,response.getBody().getOrderId());
        assertEquals("Food Palace",response.getBody().getRestaurant().getName());
        assertEquals("Aniket",response.getBody().getUserDTO().getUserName());

        verify(orderService,times(1)).saveOrder(inputOrder);

    }

}
