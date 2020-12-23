package com.lryepoch.dao;

import com.lryepoch.entity.product.RateWeight;

/**
 * @author lryepoch
 * @date 2020/10/9 17:34
 * @description TODO 设置和获取得分的权重
 */
public interface RateWeightMapper {

    void setWeight(RateWeight rateWeight);

    RateWeight getRateWeight();
}
