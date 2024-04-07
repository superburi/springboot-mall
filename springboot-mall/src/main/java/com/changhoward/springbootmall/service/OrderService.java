package com.changhoward.springbootmall.service;

import com.changhoward.springbootmall.dto.CreateOrderRequest;
import com.changhoward.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
