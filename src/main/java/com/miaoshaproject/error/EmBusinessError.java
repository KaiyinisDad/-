package com.miaoshaproject.error;

import com.miaoshaproject.response.CommonReturnType;

public enum EmBusinessError implements CommonError {
    //定义通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"parameter is not legal"),
    UNKNOWN_ERROR(10002,"unknown error"),
    //2000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"user doesn't exist"),
    USER_LOGIN_FAIL(20002,"telphone or password is wrong!"),
    USER_NOT_LOGIN(20003,"user  not login!"),

    //3000开头为交易信息相关错误定义
    STOCK_NOT_ENOUGH(30001,"stock is not enough!");
    private EmBusinessError(int errCode,String errMsg){
        this.errMsg=errMsg;
        this.errCode=errCode;
    }
    private int errCode;

    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
