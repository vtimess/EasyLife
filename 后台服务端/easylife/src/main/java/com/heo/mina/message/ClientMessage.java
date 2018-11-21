package com.heo.mina.message;

import java.util.Date;

public class ClientMessage implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private Integer code;    //0 聊天信息    1 通知

    private Integer toUserId;

    private Integer formUserId;

    private String msg;

    private Date date;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getFormUserId() {
        return formUserId;
    }

    public void setFormUserId(Integer formUserId) {
        this.formUserId = formUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "code=" + code +
                ", toUserId=" + toUserId +
                ", formUserId=" + formUserId +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}
