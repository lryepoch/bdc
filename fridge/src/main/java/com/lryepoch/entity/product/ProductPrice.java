package com.lryepoch.entity.product;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/10/13 19:38
 * @description TODO
 */
@Data
@Entity
@Table(name = "product_price")
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JSONField(serialize = false)
    private Integer id;
    @Column
    @JSONField(serialize = false)
    private String model;
    @Column
    private double price;
    private String distributionChannel;
    @JSONField(format = "yyyy-MM-dd")
    private Date activeTime;
    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    @JSONField(serialize = false)
    private int deleted;

}
