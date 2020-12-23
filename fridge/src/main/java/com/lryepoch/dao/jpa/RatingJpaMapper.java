package com.lryepoch.dao.jpa;

import com.lryepoch.entity.product.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author lryepoch
 * @date 2020/10/10 9:55
 * @description TODO
 */
public interface RatingJpaMapper extends JpaRepository<ProductRating, Integer> {

    boolean existsByUidAndMid(int uid, Integer mid);

    Optional<ProductRating> findByUidAndMid(int uid, int mid);
}
