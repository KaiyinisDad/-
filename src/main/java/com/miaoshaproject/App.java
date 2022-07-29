package com.miaoshaproject;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
//最开始是enableautoapplication，自动启动一个内嵌的tomcat
//springbootapplication使这个类被spring托管
@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})//指定为主启动类，被springboot托管
//这个和底下的requestmapping可以起到一个springmvc的作用
@RestController
@MapperScan("com.miaoshaproject.dao")//把dao存放的地方设置在对应的这个注解下
public class App {
    @Autowired
    private UserDOMapper userDOMapper;
    @RequestMapping("/")
    public String home(){
        UserDO userDO=userDOMapper.selectByPrimaryKey(1);
        if(userDO==null){
            return"用户对象不存在";
        }else{
            return userDO.getName();
        }

    }
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
