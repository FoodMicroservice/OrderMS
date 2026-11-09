package org.codedecode.orderms.mapper;

import org.codedecode.orderms.dto.OrderDTO;
import org.codedecode.orderms.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO mapOrdertoOrderDTO(Order order);
    Order mapOrderDTOtoOrder(OrderDTO orderDTO);
}
