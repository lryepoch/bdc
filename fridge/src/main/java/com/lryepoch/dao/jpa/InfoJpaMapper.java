package com.lryepoch.dao.jpa;

import com.lryepoch.entity.product.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lryepoch
 * @date 2020/10/31 15:12
 * @description TODO
 */
public interface InfoJpaMapper extends JpaRepository<ProductInfo, Integer> {
}
