package com.lryepoch.dao.jpa;

import com.lryepoch.entity.product.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lryepoch
 * @date 2020/10/10 9:00
 * @description TODO
 */
public interface ProductInfoMapper extends JpaRepository<ProductInfo, Integer> {
}
