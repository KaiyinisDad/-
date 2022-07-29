package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//controller名字是user，用来被spring扫描到
@Controller("user")
//在url上面需要通过/user这种方式访问
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends  BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;//通过bean注入，就是单例模式。那么单例模式怎么支持多个用户并发访问呢？其实他是被springbean包装的h，本质是proxy，内部有threadlocal方式的map，让用户在每个线程中处理自己的request，并且有threadlocal清除的机制。
    //用户登录接口
    @RequestMapping(value="/login",method={RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telphone")String telphone,@RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验，对应手机号和密码不能为空
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)|| StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，用来校验用户登录是否合法
        UserModel userModel=userService.validateLogin(telphone,this.EncodeByMd5(password));
        //将登录凭证加入到用户登陆成功的session内，
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create(null);



    }
    //用户注册接口
    @RequestMapping(value="/register",method={RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String telphone,@RequestParam(name="otpCode")String otpCode,@RequestParam(name="name")String name,@RequestParam(name="gender")Byte gender,@RequestParam(name="age")Integer age,@RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otp相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        System.out.println(inSessionOtpCode);
        //this.httpServletRequest.getParameter("otpCode");
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //合法的话，就进入用户的注册流程
        UserModel userModel=new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelephone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncryptPassword(this.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);

    }
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en=new BASE64Encoder();
        //加密字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    //用户获取otp短信的接口
    @RequestMapping(value="/getotp",method={RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone){
        //需要按照一定规则生成OTP验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);
        randomInt += 100000;
        String otpCode=String.valueOf(randomInt);
        //将OTP验证码同对应用户的手机号关联,使用httpsession的方式绑定
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将OTP验证码通过短信通道发送给用户，省略，涉及到短信通道流程
        System.out.println("telphone = "+telphone+"&otpCode = "+otpCode);
        return  CommonReturnType.create(null);

    }
    @RequestMapping("/get")
    @ResponseBody
    //本来返回值是UserModel，但是UserModel里保留了很多不需要展示给前端的东西，比如用户的密码，因此新建了UserVO类，作为新的返回值
    //然后返回值是UserVO,但是考虑到前端status的问题，改为commonreturntype
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务获取对应用户对象并返回给前端
        UserModel userModel=userService.getUserById(id);
        //若获取的对应用户信息不存在
        if(userModel==null){
            //userModel.setEncryptPassword("123");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //将核心领域用户对象转化为可供UI使用的viewobject
        UserVO userVO= convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }
    private UserVO convertFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }else{
            UserVO userVO=new UserVO();
            BeanUtils.copyProperties(userModel,userVO);
            return userVO;
        }
    }


}
