package com.lryepoch.entity.product;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lryepoch
 * @date 2020/10/10 9:01
 * @description TODO
 */
@Getter
@Setter
@Entity
@Table(name = "product_info")
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String dataSource;
    private String brand;
    private String model;
    private String panelMaterial;
    private String color;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    private String door;
    private double totalVolume;
    private double refrigeVol;
    private double freezerVol;
    private double variableHouseVol;
    private Long tempAreaNum;
    private double productWidth;
    private double productHeight;
    private double productDepth;
    private double platformWidth;
    private double platformHeight;
    private double platformDepth;
    private Double packingWidth;
    private Double packingHeight;
    private Double packingDepth;

    @JSONField(format = "yyyy-MM-dd")
    private Date marketTime;
    private double weight;
    private String climateType;
    private String level;
    private String systemType;
    private double compPowerConsumption;
    private double coolingCapacity;
    private double noise;
    private String compressor;
    private String refrigerationMode;
    private String coolingMode;
    private String refrigerant;
    private String ratedVoltage;
    private String coldTempRange;
    private String freezingTempRange;
    private String variableTempRange;
    private String panelTechnology;
    private String rackNum;
    private String boxNum;
    private String variableDrawerNum;
    private String aquariusNum;
    private String freezingDrawerNum;
    private String foldbleRackNum;
    private String eggboxNum;
    private String wineRackNum;
    private String refrigeratedLamp;
    private String barNum;
    private String iceMachineNum;
    private String quickFreezing;
    private String displayMode;
    private String childLock;
    private String holidayFunction;
    private String tempControlMode;
    private String doorAlarm;
    private String features;
    private String wifi;
    private String bactericidalFunction;
    private Double warrantyYears;
    private int deleted;

}
