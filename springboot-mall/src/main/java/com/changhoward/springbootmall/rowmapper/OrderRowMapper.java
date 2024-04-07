package com.changhoward.springbootmall.rowmapper;

import com.changhoward.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setCreatedDate(rs.getTimestamp("created_date"));
        order.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return order;

    }

}
