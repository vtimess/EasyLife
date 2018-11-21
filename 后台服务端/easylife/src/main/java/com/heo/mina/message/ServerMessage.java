package com.heo.mina.message;

import javax.persistence.Entity;

public class ServerMessage implements java.io.Serializable{

    private static final long serialVersionUID = 2L;

    private  Integer code;    //0:心跳包  1:连接  -1:下线  2:转发信息

    private Integer fromUserId; //你是谁

    private Integer toUserId;    //发给谁

    private String msg; //  发送的信息

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }
}
