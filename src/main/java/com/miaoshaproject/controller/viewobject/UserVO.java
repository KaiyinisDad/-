package com.miaoshaproject.controller.viewobject;

public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    //https://blog.csdn.net/qq_34115899/article/details/114314628 java和mysql字段类型对照表
    private Integer age;

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

    private String telephone;
    /*这个类是展示给前端的，所以下面三个属性没必要
    private String registerMode;
    private String thirdPartyId;
    private String encryptPassword;

     */
}
