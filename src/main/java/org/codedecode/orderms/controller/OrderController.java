package org.codedecode.orderms.controller;

import org.codedecode.orderms.dto.OrderDTO;
import org.codedecode.orderms.dto.OrderDTOfromFrontend;
import org.codedecode.orderms.repository.OrderRepo;
import org.codedecode.orderms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService  orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTOfromFrontend  orderDetails) {
        OrderDTO orderSaved=orderService.saveOrder(orderDetails);
        return new ResponseEntity<>(orderSaved, HttpStatus.CREATED);
    }

}
