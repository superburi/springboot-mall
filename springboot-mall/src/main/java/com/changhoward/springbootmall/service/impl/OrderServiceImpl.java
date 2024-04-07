package com.changhoward.springbootmall.service.impl;

import com.changhoward.springbootmall.dao.OrderDao;
import com.changhoward.springbootmall.dao.ProductDao;
import com.changhoward.springbootmall.dto.BuyItem;
import com.changhoward.springbootmall.dto.CreateOrderRequest;
import com.changhoward.springbootmall.model.Order;
import com.changhoward.springbootmall.model.OrderItem;
import com.changhoward.springbootmall.model.Product;
import com.changhoward.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {

        // 取得訂單資訊
        Order order = orderDao.getOrderById(orderId);

        // 取得訂單明細資訊
        // 因為一張訂單會包含一個orderItemList(裡面是 1 至多個 orderItem)，所以有在 Order 裡面新增過欄位了
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);

        return order;

    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        List<OrderItem> orderItemList = new ArrayList<>();

        int totalAmount = 0;

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            // 取出每一個商品
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount += amount;

            // 轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;

    }

}
