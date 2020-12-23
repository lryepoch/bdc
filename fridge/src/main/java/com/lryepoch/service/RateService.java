package com.lryepoch.service;

import com.alibaba.fastjson.JSONObject;
import com.lryepoch.entity.dto.columns.UserRatingDTO;
import com.lryepoch.entity.product.ProductInfo;
import com.lryepoch.entity.product.ProductPrice;
import com.lryepoch.entity.product.RateWeight;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/9 17:23
 * @description TODO
 */
public interface RateService {
    void setRateWeight(RateWeight rateWeight);

    RateWeight getRateWeight();

    /*根据id获取产品评分*/
    UserRatingDTO getUserAvgRating(int id);

    JSONObject getTotalRating(ProductInfo in2, List<ProductPrice> parseArray, UserRatingDTO ratingDTO);
}
