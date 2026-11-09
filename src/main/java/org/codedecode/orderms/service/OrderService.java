package org.codedecode.orderms.service;

import org.codedecode.orderms.dto.OrderDTO;
import org.codedecode.orderms.dto.OrderDTOfromFrontend;
import org.codedecode.orderms.dto.UserDTO;
import org.codedecode.orderms.entity.Order;
import org.codedecode.orderms.mapper.OrderMapper;
import org.codedecode.orderms.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepo  orderRepo;

    @Autowired
    SequenceGenrator sequenceGenrator;

    @Autowired
    RestTemplate restTemplate;
    public OrderDTO saveOrder(OrderDTOfromFrontend orderDetails) {
//generate next orderid
        Integer newOrderId=sequenceGenrator.generateNextOrder();
        //we need to user MC to get UserInfo
         UserDTO userDTO= fetchOrderDetailsFromUserId(orderDetails.getUserId());
         Order orderToSave=new Order(newOrderId,orderDetails.getFoodItems(),orderDetails.getRestaurant(),userDTO);
         orderRepo.save(orderToSave);
        return OrderMapper.INSTANCE.mapOrdertoOrderDTO(orderToSave);
    }

    private UserDTO fetchOrderDetailsFromUserId(Integer userId) {
        String url="http://userInfo/user/fetchUserById/";
       return restTemplate.getForObject(url+userId,UserDTO.class);
    }

    //we also have autowired the sequence generator

}
