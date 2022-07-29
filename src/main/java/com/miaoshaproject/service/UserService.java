package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {
    //通过用户id获取用户对象的方法
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    /*
    telphone:the number used to be register
    password: the encrypted password
     */
    UserModel validateLogin(String telphone,String encryptPassword) throws BusinessException;
}
