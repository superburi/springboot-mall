package com.changhoward.springbootmall.service.impl;

import com.changhoward.springbootmall.dao.ProductDao;
import com.changhoward.springbootmall.model.Product;
import com.changhoward.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

}
