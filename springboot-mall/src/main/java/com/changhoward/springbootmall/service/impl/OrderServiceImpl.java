package com.changhoward.springbootmall.service.impl;

import com.changhoward.springbootmall.dao.OrderDao;
import com.changhoward.springbootmall.dao.ProductDao;
import com.changhoward.springbootmall.dao.UserDao;
import com.changhoward.springbootmall.dto.BuyItem;
import com.changhoward.springbootmall.dto.CreateOrderRequest;
import com.changhoward.springbootmall.model.Order;
import com.changhoward.springbootmall.model.OrderItem;
import com.changhoward.springbootmall.model.Product;
import com.changhoward.springbootmall.model.User;
import com.changhoward.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

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

        /*--------------先檢查 user 是否存在--------------*/
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        /*--------------先檢查 user 是否存在--------------*/

        /*--------------計算總價、檢查庫存資訊、確實扣除庫存，並把 BuyItem 轉為 OrderItem --------------*/
        List<OrderItem> orderItemList = new ArrayList<>();

        int totalAmount = 0;

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            // 取出每一個商品
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 product 是否存在、庫存是否足夠
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 庫存足夠，扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
        /*--------------計算總價，並把 BuyItem 轉為 OrderItem --------------*/

        /*--------------實際創建訂單--------------*/
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);
        /*--------------實際創建訂單--------------*/

        return orderId;

    }

}
