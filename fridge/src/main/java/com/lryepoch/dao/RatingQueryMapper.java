package com.lryepoch.dao;

import com.lryepoch.entity.dto.columns.UserRatingDTO;
import com.lryepoch.entity.vo.ProductRatingVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author lryepoch
 * @date 2020/10/10 10:27
 * @description TODO
 */
public interface RatingQueryMapper {

    void insertRatingById(@Param("uid") int uid, @Param("vo") ProductRatingVO productRatingVO);

    UserRatingDTO getUserAvgRating(int id);
}
