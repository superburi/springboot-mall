package com.changhoward.springbootmall.dao;

import com.changhoward.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

}
