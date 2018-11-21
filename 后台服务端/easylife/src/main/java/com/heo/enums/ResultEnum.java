package com.heo.enums;


public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    PHONENUMBER_EXISTED(100,"该手机号码已经被注册过"),
    VCODE_ERROR(101,"验证码错误"),
    PHONENUMBER_NO_REGISTER(200,"该手机号码还未注册"),
    PASSWORD_ERROR(201,"密码错误"),
    HELP_FIND_NO(304,"任务找不到了"),
    HELP_ACCEPTED(305,"任务已经被别人接受了"),
    REQUEST_EXCEPTION(404,"请求异常"),
    IMAGE_LOAD_FAIL(401,"图片上传失败"),
    MONEY_NO_ENOUGH(501,"余额不足,请充值"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
