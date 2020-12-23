package com.lryepoch.dao.jpa;

import com.lryepoch.entity.product.ReptileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author lryepoch
 * @date 2020/10/31 15:51
 * @description TODO
 */
public interface ReptileJpaMapper extends JpaRepository<ReptileInfo, Integer> {
    Optional<ReptileInfo> findByModel(String model);
}
