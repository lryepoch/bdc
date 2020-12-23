package com.lryepoch.config.entity;

import lombok.Getter;

/**
 * @author lryepoch
 * @date 2020/10/31 14:05
 * @description TODO
 */
@Getter
public enum  ServiceEnum {

    //价格表名
    PRICE_TABLE_NAME("product_price"),
    //基本信息表名
    INFO_TABLE_NAME("product_info"),

    PERFORMANCE("性能指标得分"),
    ECONOMY("经济指标得分"),
    AESTHETICINDEX("审美指标得分"),
    EASE("使用便捷性指标得分")
    ;

    private String string;

    ServiceEnum(String string) {
        this.string = string;
    }

}
