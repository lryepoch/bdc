package com.lryepoch.entity.product;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author lryepoch
 * @date 2020/10/10 9:57
 * @description TODO 用户评分表
 */
@Data
@Entity
public class ProductRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int uid;
    private int mid;
    private double profiling;
    private double color;
    private double interior;
    private double light;

    private double ergonomics;
    private double storage;
    private double operation;

}
