package com.miaoshaproject.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//这才是处理用户的核心模型，dataobject里的两个类仅仅是简单映射
public class UserModel {
    private Integer id;
    @NotBlank(message="user name cannot be empty")
    private String name;
    @NotNull(message = "you should choose your gender")
    private Byte gender;
    @NotNull(message = "you should choose your age")
    @Min(value=0,message = "age should bigger than 0")
    @Max(value=150,message = "age should smaller than 150")
    private Integer age;
    @NotBlank(message="telephone cannot be empty")
    private String telephone;
    private String registerMode;
    private String thirdPartyId;
    @NotBlank(message="password cannot be empty")
    private String encryptPassword;//虽然分给了另一张表，但是属于user这个对象

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }


}
