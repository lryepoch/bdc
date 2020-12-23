package com.lryepoch.entity.product;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lryepoch
 * @date 2020/10/13 8:57
 * @description TODO
 */
@Getter
@Setter
@Entity
@Table(name = "product_professional_parameters")
public class ProductProfessionalParameters {

    private Integer id;
    @Id
    @GeneratedValue(generator = "model-id")
    //自定义主键生成策略，由@GenericGenerator实现。
    //如果实际应用中，主键策略为程序指定了就用程序指定的主键（assigned），没有指定就从sequence中取。
    @GenericGenerator(name = "model-id", strategy = "assigned")
    private String model;
    private String compression;
    private String manufacturerModel;
    private String displacement;
    private String power;
    private String cop;
    private String suctionPipeSpecification;
    private String exhaustPipeSpecification;
    private String processPipeSpecification;
    private String capacity;
    private String refrigeratingOutput;
    private String condenserPicture;
    private String condenserType;
    private String condenserHeatExchange;
    private String condenserInsideVol;
    private String condenserHeatTransfer;
    private String condenserDryFilter;
    private String anticoagulantHeatExchange;
    private String anticoagulantLocation;
    private String mufflerPicture;
    private String mufflerType;
    private String mufflerHeatExchange;
    private String mufflerCapillary;
    private String mufflerEffictiveExchangeLength;
    private String refrigeratedCapillarySpecification;
    private String variableCapillarySpecification;
    private String freezingCapillarySpecification;
    private String refriEvaporatorPicture;
    private String refriEvaporatorType;
    private String refriEvaporatorHeatExchange;
    private String refriEvaporatorFinSpecification;
    private String refriEvaporatorFinDistance;
    private String refriEvaporatorInsideVol;
    private String refriEvaporatorHeatTransfer;
    private String refriEvaporatorReservoir;
    private String refriEvaporatorSortType;
    private String refriEvaporatorPipeDistance;
    private String variableEvaporatorPicture;
    private String variableEvaporatorType;

    private String variableEvaporatorHeatExchange;
    private String variableEvaporatorFin;
    private String variableEvaporatorFinDistance;
    private String variableEvaporatorInsideVol;
    private String variableEvaporatorHeatTransfer;
    private String variableEvaporatorReservoir;
    private String variableEvaporatorSortType;
    private String variableEvaporatorPipeDistance;
    private String freezingEvaporatorPicture;
    private String freezingEvaporatorType;
    private String freezingEvaporatorHeatExchange;
    private String freezingEvaporatorFin;
    private String freezingEvaporatorFinDistance;
    private String freezingEvaporatorInsideVol;
    private String freezingEvaporatorHeatTransfer;
    private String freezingEvaporatorReservoir;
    private String freezingEvaporatorSortType;
    private String freezingEvaporatorPipeDistance;
    private String exhaustConnectionPipePicture;
    private String exhaustConnectionHeatTransfer;
    private String variableDeforstHeaterPicture;
    private String variableDeforstHeaterPower;
    private String freezingDeforstHeaterPicture;
    private String freezingDeforstHeaterPower;
    private String refriFanPicture;

    private String refriFanManufacturerModel;
    private String refriFanBlowingRate;
    private String variableFanPicture;
    private String variableFanManufacturerModel;
    private String variableFanBlowingRate;
    private String freezingFanPicture;
    private String freezingFanManufacturerModel;
    private String freezingFanBlowingRate;
    private String foamingThickness1;
    private String foamingThickness2;
    private String foamingThickness3;
    private String foamingThickness4;
    private String foamingThickness5;
    private String foamingThickness6;
    private String foamingThickness7;
    private String foamingThickness8;
    private String foamingThickness9;
    private String foamingThickness10;
    private String foamingThickness11;
    private String foamingThickness12;
    private String foamingThickness13;
    private String appearanceDistance1;
    private String appearanceDistance2;
    private String appearanceDistance3;
    private int deleted;

}
