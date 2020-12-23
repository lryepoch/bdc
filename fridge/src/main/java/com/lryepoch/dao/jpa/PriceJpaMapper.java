package com.lryepoch.dao.jpa;

import com.lryepoch.entity.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/13 19:37
 * @description TODO
 */
public interface PriceJpaMapper extends JpaRepository<ProductPrice, Integer> {
    /**
    * @description 根据型号去删除原来的价格，因为这里是先清掉了所有原来的价格，然后把修改后的价格重新写入，这里就直接去掉了原来的记录了
    * @author lryepoch
    * @date 2020/10/13 19:44
    *
    */
    void deleteByModel(String model);

    /**
    * @description 通过单个机型查询所有正式表价格
    * @author lryepoch
    * @date 2020/11/12 8:47
    *
    */
    @Query(value = "select id, active_time, deleted, distribution_channel, model, price, update_time from product_price where model=?1 and deleted=1 order by active_time desc", nativeQuery = true)
    List<ProductPrice> findByModelOrderByActiveTimeDesc(String model);

    /**
    * @description 通过机型列表查询所有正式表价格
    * @author lryepoch
    * @date 2020/11/12 8:47
    *
    */
    @Query(value = "select id, active_time, deleted, distribution_channel, model, price, update_time from product_price where model in (?1) and deleted=1 order by active_time desc", nativeQuery = true)
    List<ProductPrice> findByModelListOrderByActiveTimeDesc(List<String> modelList);

    /**
    * @description 通过单个机型查询所有爬虫表价格
    * @author lryepoch
    * @date 2020/11/12 8:48
    *
    */
    @Query(value = "select id, active_time, deleted, distribution_channel, model, price, update_time from new_product_price where model=?1 and deleted=1 order by active_time desc", nativeQuery = true)
    List<ProductPrice> findReptilePriceByModelOrderByActiveTimeDesc(String model);
}
