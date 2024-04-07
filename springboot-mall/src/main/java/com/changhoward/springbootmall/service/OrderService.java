package com.changhoward.springbootmall.service;

import com.changhoward.springbootmall.dto.CreateOrderRequest;
import com.changhoward.springbootmall.dto.OrderQueryParams;
import com.changhoward.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
