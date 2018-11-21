package com.heo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Wallet {

    @Id
    private Integer userId;

    private float money = 0;

    private String payPassword;

    public Wallet() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
