package com.lryepoch.entity.vo;

import lombok.Data;

import java.util.LinkedList;

/**
 * @author lryepoch
 * @date 2020/11/10 14:40
 * @description TODO 查询页面数据筛选器，均需要多选
 */
@Data
public class InfoFilterVO {
    /*页码*/
    private String page;
    /*每页行数*/
    private String row;

    private String model;
    private LinkedList<String> dataSource;
    private LinkedList<String> brand;
    private LinkedList<String> updateTime;
    private LinkedList<String> price;
    private LinkedList<String> door;
    private LinkedList<String> totalVolume;
    private LinkedList<String> level;
    private LinkedList<String> compPowerConsumption;
    private LinkedList<String> noise;
    private LinkedList<String> compressor;
    private LinkedList<String> platformWidth;
    private LinkedList<String> platformHeight;
    private LinkedList<String> platformDepth;
    private LinkedList<String> panelMaterial;
    private LinkedList<String> refrigerationMode;
    private LinkedList<String> refrigeratedLamp;
    private LinkedList<String> refrigerant;
    private LinkedList<String> marketTime;
    private LinkedList<String> displayMode;
    private LinkedList<String> tempControlMode;
    private LinkedList<String> climateType;
    private LinkedList<String> wifi;
    private LinkedList<String> systemType;
    private LinkedList<String> coolingMode;
    private LinkedList<String> tempAreaNum;
    private LinkedList<String> distributionChannel;
    private LinkedList<String> excludeMachine;
}
