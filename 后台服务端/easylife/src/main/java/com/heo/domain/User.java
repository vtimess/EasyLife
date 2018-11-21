package com.heo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    private Integer userId;

    private String phoneNumber;

    //姓名
    private String name;

    //头像
    private String headImage;

    //性别  boy girl
    private String sex = "保密";

    //学校
    private String school;

    //专业
    private String major;

    //年级
    private String grade;

    //学号
    private String number;

    //签名
    private String signature;

    //家乡
    private String hometown;

    //生日
    private String birthday;

    //信誉度5分值
    private float credit = 4;

    //是否有账单还未结账
    private boolean lack = false;

    private Integer canceNumber = 0 ; //取消任务的次数

    //备注
    private String notes;

    public User() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public boolean isLack() {
        return lack;
    }

    public void setLack(boolean lack) {
        this.lack = lack;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getCanceNumber() {
        return canceNumber;
    }

    public void setCanceNumber(Integer canceNumber) {
        this.canceNumber = canceNumber;
    }
}
