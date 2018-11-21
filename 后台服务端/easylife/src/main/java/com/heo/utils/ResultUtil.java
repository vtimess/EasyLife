package com.heo.utils;

import com.heo.domain.Result;

public class ResultUtil {

    public static Result success(String msg, Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(Integer...parm) {
        Result result = new Result();
        result.setCode(2);
        String msg = "请求异常 code:";
        for (Integer p: parm) {
            msg += p +" ";
        }
        result.setMsg(msg);
        return result;
    }
}
