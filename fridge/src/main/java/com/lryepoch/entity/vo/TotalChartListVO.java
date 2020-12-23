package com.lryepoch.entity.vo;

import lombok.Data;

/**
 * @author lryepoch
 * @date 2020/11/11 12:32
 * @description  图表数据中的list实体
 */
@Data
public class TotalChartListVO {
    private String dataSource;
    private String brand;
    private String level;
    private String door;
    private String displayMode;
    private String model;
    private String totalVolume;
    private String compressor;
    private String refrigerationMode;
    private String platformWidth;
    private String platformDepth;
}
