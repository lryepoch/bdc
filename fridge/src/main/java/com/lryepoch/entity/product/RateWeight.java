package com.lryepoch.entity.product;

import lombok.Data;

import javax.persistence.*;

/**
 * @author lryepoch
 * @date 2020/10/9 16:32
 * @description TODO 评分权重实体
 */
@Data
@Entity
@Table(name = "product_rate_weight")
public class RateWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /******************
    * 性能指标得分
    */
    private Double performanceWeight;

    /**
    * 冰箱能效等级
    */
    private Double levelWeight;

    /**
    * 冰箱噪音值
    */
    private Double noiseWeight;

     /**
     * 冰箱冷冻能力
     */
    private Double refriWeight;

    /**
    * 冰箱容积率
    */
    private Double totalVolWeight;

    /**
    * 冰箱气候类型
    */
    private Double climateWeight;

    /****************
    * 经济指标
    */
    private Double economyWeight;

    /**
    * 冰箱售价
    */
    private Double priceWeight;

    /**
    * 冰箱保修年限
    */
    private Double warrantYearWeight;

    /****************
    * 审美指标
    */
    private Double aestheticIndexWeight;

    /**
    * 造型评价
    */
    private Double profilingWeight;

    /**
    * 色彩评价
    */
    private Double colorWeight;

    /**
    * 内饰精致性评价
    */
    private Double interiorWeight;

    /**
    * 灯光评价
    */
    private Double lightWeight;

    /***************
    * 使用便捷性指标
    */
    private Double easeWeight;

    /**
    * 人体工学评价
    */
    private Double ergonomicsWeight;

    /**
    * 存储分区评价
    */
    private Double storageWeight;

    /**
    * 操作易用性评价
    */
    private Double operationWeight;

}
