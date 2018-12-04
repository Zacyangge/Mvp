package com.example.chenzaoyang.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Zac
 * 邮箱：1105202941@qq.com
 */
@Entity
public class UserInfo {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String age;
    private String name;
    private String gender;
    private String hobby;
    @Id
    private Long id;
    @Generated(hash = 579964280)
    public UserInfo(String age, String name, String gender, String hobby, Long id) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.hobby = hobby;
        this.id = id;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }
}