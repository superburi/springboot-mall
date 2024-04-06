package com.changhoward.springbootmall.dao;

import com.changhoward.springbootmall.dto.UserRegisterRequest;
import com.changhoward.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
