package com.heo.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Help {

    @Id
    @GeneratedValue
    private Integer helpId; //帮助ID

    @NotNull(message = "不是合法的请求 userId")
    private Integer userId; //用户ID

    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate; //发布日期

    @Min(value = 59 ,message = "你的信誉度太低了，不能发布请求，请多帮助别人提升自己的信誉度吧")
    private float credit; //信誉度

    @NotNull(message = "标题不能为空")
    private String title; //标题

    private String classname; //类型

    private String content; //内容

    @NotNull(message = "佣金不能为空")
    private float money; //佣金

    private String imagesUrl; //任务发布的图片  可以多张用  "；"拆分

    private Integer status = 0; //状态  0:发布中 1:进行中 2:未付款  3:已完成 4：以评价

    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptDate; //接受日期

    private Integer acceptUserId; //接收人id

    @Temporal(TemporalType.TIMESTAMP)
    private Date completeDate; //完成日期

    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate; //支付时间

    private Integer sexLimit  = 0;   //1:男  0：不限制 -1：女


    public Help() {

    }

    public Integer getHelpId() {
        return helpId;
    }

    public void setHelpId(Integer helpId) {
        this.helpId = helpId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Integer getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Integer acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public Integer getSexLimit() {
        return sexLimit;
    }

    public void setSexLimit(Integer sexLimit) {
        this.sexLimit = sexLimit;
    }
}
