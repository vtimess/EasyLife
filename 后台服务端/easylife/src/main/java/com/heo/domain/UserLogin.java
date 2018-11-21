package com.heo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserLogin {

    @Id
    @GeneratedValue
    private Integer userId;

    @NotNull(message = "手机号不能为空")
    @Size(min = 11, max = 11,message = "手机号码不合法")
    private String phoneNumber;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码长度不合法")
    private String password;

    //状态代吗
    private Integer Status;

    public UserLogin() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
