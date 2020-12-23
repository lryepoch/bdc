package com.lryepoch.config.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lryepoch
 * @date 2020/5/27 15:45
 * @description 返回值的最外层数据结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult implements Serializable {

    private static final long serialVersionUID = -2884790828192232980L;

    /**状态*/
    private boolean status;

    /**状态码*/
    private Integer code;

    /**状态信息*/
    private String msg;

    /**数据*/
    private Object data;

    //是否登录
//    private boolean isLogin=true;

    //查询
    public static CommonResult success(Object data){
        return CommonResult.builder().status(true).code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getMsg()).data(data).build();
    }

    //增删改
    public static CommonResult success(Integer code, String msg){
        return CommonResult.builder().status(true).code(code).msg(msg).build();
    }

    //增删改
    public static CommonResult success(){
        return success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    //失败
    public static CommonResult fail(Integer code, String msg){
        return CommonResult.builder().status(false).code(code).msg(msg).build();
    }

    //失败数据(集合)
    public static CommonResult fail(Object data){
        return CommonResult.builder().status(false).code(ResultEnum.ERR.getCode()).msg(ResultEnum.ERR.getMsg()).data(data).build();
    }



}
