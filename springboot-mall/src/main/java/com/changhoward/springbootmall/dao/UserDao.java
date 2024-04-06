package com.changhoward.springbootmall.dao;

import com.changhoward.springbootmall.dto.UserRegisterRequest;
import com.changhoward.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
