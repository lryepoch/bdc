package com.lryepoch.dao;

import com.lryepoch.entity.product.ProductPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/10 18:44
 * @description TODO
 */
public interface PriceQueryMapper {

    void deletePriById(List<Integer> ids);

    List<ProductPrice> selectMatchedPriceFromReptile();

    void insertUpdatePrice(@Param("priceList") List<ProductPrice> priceList);

    void deleteReptilePriceByWeekUpdate();

    List<Integer> selectMatchIdFromReptile();
}
