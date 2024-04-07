package com.changhoward.springbootmall.service;

import com.changhoward.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
