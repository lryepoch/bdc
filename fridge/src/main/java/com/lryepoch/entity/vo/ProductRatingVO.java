package com.lryepoch.entity.vo;

import lombok.Data;

/**
 * @author lryepoch
 * @date 2020/10/9 19:31
 * @description TODO 产品评分VO
 */
@Data
public class ProductRatingVO {

    private Integer id;
    /**
    * 造型
    */
    private Double profiling;
    /**
     * 色彩
     */
    private Double color;
    /**
     * 内饰
     */
    private Double interior;
    /**
     * 灯光
     */
    private Double light;

    /***********
     * 人体工学
     */
    private Double ergonomics;
    /**
     * 存储分区
     */
    private Double storage;
    /**
     * 操作易用性
     */
    private Double operation;
}
