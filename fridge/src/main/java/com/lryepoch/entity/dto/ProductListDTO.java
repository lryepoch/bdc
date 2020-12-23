package com.lryepoch.entity.dto;

import com.lryepoch.entity.product.ProductPrice;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/11/10 17:29
 * @description TODO 返回给页面的产品列表，包含品牌、门体、型号、数据来源以及图片地址
 */
@Data
public class ProductListDTO {
    private Integer id;
    private String dataSource;
    private String brand;
    private String level;
    private String door;
    private String displayMode;
    private String model;
    private String totalVolume;
    private String compressor;
    private String refrigerationMode;
    private Double platformWidth;
    private Double platformDepth;
    private Double newestPrice;
    private List<ProductPrice> prices;
    private String pictureUrl;

    private Date updateTime;
    private Boolean priceReminder;
}
