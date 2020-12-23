package com.lryepoch.entity.dto.columns;

import lombok.Data;

/**
 * @author lryepoch
 * @date 2020/11/11 14:12
 * @description TODO 用户评分DTO
 */
@Data
public class UserRatingDTO {
    private Double profiling;
    private Double color;
    private Double interior;
    private Double light;
    private Double ergonomics;
    private Double storage;
    private Double operation;
}
