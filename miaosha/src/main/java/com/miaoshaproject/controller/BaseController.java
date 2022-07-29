package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
//@ResponseBody
public class BaseController {
    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";
    //以前在UserController下
    //throw出来的exception会被抛到了tomcat的容器层，tomcat的对应处理方式就是返回一个500的错误页。好在springboot为我们提供了一个通用化的解决方式，可以拦截掉tomcat的处理方式，那就是定义controller层解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData=new HashMap<>();
        if(ex instanceof BusinessException){
            BusinessException businessException=(BusinessException)ex;


            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else{

            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }


        return CommonReturnType.create(responseData,"fail");
        /*
        CommonReturnType commonReturnType=new CommonReturnType();
        commonReturnType.setStatus("fail");


        commonReturnType.setData(responseData);

        return commonReturnType;

         */
    }
}
