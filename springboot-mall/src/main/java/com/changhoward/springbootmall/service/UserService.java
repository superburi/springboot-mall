package com.changhoward.springbootmall.service;

import com.changhoward.springbootmall.dto.UserLoginRequest;
import com.changhoward.springbootmall.dto.UserRegisterRequest;
import com.changhoward.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

}
