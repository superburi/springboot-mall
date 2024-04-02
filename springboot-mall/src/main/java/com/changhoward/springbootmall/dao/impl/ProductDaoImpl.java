package com.changhoward.springbootmall.dao.impl;

import com.changhoward.springbootmall.dao.ProductDao;
import com.changhoward.springbootmall.dto.ProductRequest;
import com.changhoward.springbootmall.model.Product;
import com.changhoward.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {

        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date, product_name, category, image_url, price, stock," +
                " description, created_date, last_modified_date FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        /*
            因為如果用id去查察不到資料的話，query方法返回的就會是空集合，
            空集合還去get(0)的話，就會噴出indexoutofbounds的exception，
            所以才不能直接寫get(0)，而是要用if去包住，真的沒查到資料至少
            還可以回傳一個null，可以去做後續處理，而不是噴exception讓程式
            中止。
        */
        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description," +
                " created_date, last_modified_date)\n" +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate," +
                " :lastModifiedDate);";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int productId = keyHolder.getKey().intValue();

        return productId;

    }

}
