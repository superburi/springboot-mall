package com.changhoward.springbootmall.service;

import com.changhoward.springbootmall.dto.ProductRequest;
import com.changhoward.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

}
