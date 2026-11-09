package org.codedecode.orderms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.codedecode.orderms.dto.FoodItemsDTO;
import org.codedecode.orderms.dto.Restaurant;
import org.codedecode.orderms.dto.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private Integer orderId;
    private List<FoodItemsDTO> foodItems;
    private Restaurant restaurant;
    private UserDTO userDTO;
}
