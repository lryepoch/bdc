package com.lryepoch.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lryepoch.dao.RateWeightMapper;
import com.lryepoch.dao.RatingQueryMapper;
import com.lryepoch.entity.dto.columns.UserRatingDTO;
import com.lryepoch.entity.product.ProductInfo;
import com.lryepoch.entity.product.ProductPrice;
import com.lryepoch.entity.product.RateWeight;
import com.lryepoch.service.RateService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/9 17:23
 * @description TODO
 */
@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateWeightMapper rateWeightMapper;
    @Autowired
    private RatingQueryMapper ratingQueryMapper;

    /**
     * @description 设置计算得分的权重
     * @author lryepoch
     * @date 2020/10/9 19:05
     */
    @Override
    public void setRateWeight(RateWeight rateWeight) {
        //如果没有设置比重，则默认为0
        Field[] fields = RateWeight.class.getDeclaredFields();
        for (Field field : fields) {
            //isAccessible()的结果是false时不允许通过反射访问该字段
            //field.setAccessible(true)作用就是让我们在用反射时访问私有变量
            field.setAccessible(true);
            try {
                if (field.get(rateWeight) == null) {
                    field.set(rateWeight, 0);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        rateWeightMapper.setWeight(rateWeight);
    }

    @Override
    public RateWeight getRateWeight() {
        return rateWeightMapper.getRateWeight();
    }

    @Override
    public UserRatingDTO getUserAvgRating(int id) {
        return ratingQueryMapper.getUserAvgRating(id);
    }

    /**
     * @description 获取型号的总得分
     * @author lryepoch
     * @date 2020/11/12 11:11
     */
    @Override
    public JSONObject getTotalRating(ProductInfo info, List<ProductPrice> prices, UserRatingDTO ratingDTO) {
        RateWeight weight = rateWeightMapper.getRateWeight();

        //如果dto中的值是null,需要置为0
        Field[] fields = UserRatingDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.get(ratingDTO) == null) {
                    field.set(ratingDTO, 0);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /*计算性能指标得分*/
        //能效等级
        int level, climate;
        switch (info.getLevel()) {
            case "1":
            case "1级":
            case "新国标1级": level=100; break;
            case "2":
            case "2级":
            case "新国标2级": level=80; break;
            case "3":
            case "3级":
            case "新国标3级": level=60; break;
            case "旧国标1级": level=90; break;
            case "旧国标2级": level=70; break;
            case "旧国标3级": level=50; break;
            default:level=40;
        }
        BigDecimal levelBig = new BigDecimal(level);

        //噪音
        BigDecimal noiseBig = info.getNoise()==0?new BigDecimal(0):new BigDecimal(30*100).divide(new BigDecimal(info.getNoise()), 1, RoundingMode.HALF_UP);

        //制冷能力
        BigDecimal refriBig = new BigDecimal(info.getCoolingCapacity()).multiply(new BigDecimal(100/20));

        //容积率
        BigDecimal volBig = new BigDecimal(info.getProductWidth()).multiply(new BigDecimal(info.getProductDepth())).multiply(new BigDecimal(info.getProductHeight()));
        BigDecimal totalVolBig = volBig.equals(new BigDecimal(0))?new BigDecimal(0):new BigDecimal(info.getTotalVolume()).multiply(new BigDecimal(100*1000000)).divide(volBig, 1, RoundingMode.HALF_UP);

        //气候类型
        switch (info.getClimateType()){
            case "SN.N.ST.T": climate=100; break;
            case "SN.N.ST": climate=80; break;
            default: climate=60; break;
        }
        BigDecimal climateBig = new BigDecimal(climate);

        //性能分  冰箱能效等级得分*【0.46】+冰箱噪音值得分*【0.29】+冰箱冷冻能力得分*【0.15】+冰箱容积率得分*【0.05】+冰箱气候类型得分*【0.05】
        BigDecimal performance = levelBig.multiply(new BigDecimal(weight.getLevelWeight()))
                .add(noiseBig.multiply(new BigDecimal(weight.getNoiseWeight()))).add(refriBig.multiply(new BigDecimal(weight.getRefriWeight())))
                .add(totalVolBig.multiply(new BigDecimal(weight.getTotalVolWeight()))).add(climateBig.multiply(new BigDecimal(weight.getClimateWeight())));

        /*计算经济指标得分*/
        //取得最新的价格生效时间
        Date maxDate = null;
        if (prices.size()>0){
            maxDate=prices.parallelStream().max((p1,p2)->{
                if (p2.getActiveTime().after(p1.getActiveTime())){
                    return -1;
                }else if(p2.getActiveTime().before(p1.getActiveTime())){
                    return 1;
                } else {
                    return 0;
                }
            }).orElseGet(ProductPrice::new).getActiveTime();
        }
        Date finalMaxDate = maxDate;
        //计算均价，【均价】=最新一次价格更新的平均值，这里取最新一天时间的所有价格的平均值
        double avgPrice = prices.parallelStream().filter(productPrice -> {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(productPrice.getActiveTime()).equals(df.format(finalMaxDate));
        }).min(Comparator.comparingDouble(ProductPrice::getPrice))
            .orElseGet(ProductPrice::new).getPrice();

        //冰消售价得分
        BigDecimal priceBig = avgPrice==0?new BigDecimal(0):new BigDecimal(info.getTotalVolume()).multiply(new BigDecimal(10)).divide(new BigDecimal(avgPrice), 1, RoundingMode.HALF_UP);

        //冰箱保修年限得分，若保修年限为0，则默认按1年处理
        BigDecimal warrantyBig = new BigDecimal(info.getWarrantyYears()).compareTo(new BigDecimal(0))==0?new BigDecimal(1):new BigDecimal(info.getWarrantyYears()).multiply(new BigDecimal(100)).divide(new BigDecimal(10),1, RoundingMode.HALF_UP);

        //经济指标分 冰箱售价得分*【0.9】+冰箱保修年限得分*【0.1】
        BigDecimal economy = priceBig.multiply(new BigDecimal(weight.getPriceWeight())).add(warrantyBig.multiply(new BigDecimal(weight.getWarrantYearWeight())));

        /*计算审美指标得分*/
        /*造型评价得分*【0.4】+色彩评价得分*【0.4】+内饰精致性评价得分*【0.10】+灯光评价*【0.10】 */
        BigDecimal aestheticIndex = new BigDecimal(ratingDTO.getErgonomics()).multiply(new BigDecimal(10*weight.getErgonomicsWeight()))
                .add(new BigDecimal(ratingDTO.getStorage()).multiply(new BigDecimal(10*weight.getStorageWeight())))
                .add(new BigDecimal(ratingDTO.getOperation()).multiply(new BigDecimal(10*weight.getOperationWeight())));

        /*计算使用便捷性指标得分
        * 人体工学评价*【0.1】+存储分区评价*【0.6】+操作易用性评价*【0.3】*/
        BigDecimal ease = new BigDecimal(ratingDTO.getErgonomics()).multiply(new BigDecimal(10*weight.getErgonomicsWeight()))
                .add(new BigDecimal(ratingDTO.getStorage()).multiply(new BigDecimal(10*weight.getStorageWeight())))
                .add(new BigDecimal(ratingDTO.getOperation()).multiply(new BigDecimal(10*weight.getOperationWeight())));

        //每项都乘以10是因为用户打分范围是0~10，需要扩大10倍
        BigDecimal totalRate = performance.multiply(new BigDecimal(weight.getPerformanceWeight()))
                .add(economy.multiply(new BigDecimal(weight.getEconomyWeight()))).add(aestheticIndex.multiply(new BigDecimal(weight.getAestheticIndexWeight())))
                .add(ease.multiply(new BigDecimal(0.28)));
        //得分图
        JSONObject obj = new JSONObject(true);
        obj.put("totalRating", totalRate.setScale(1, BigDecimal.ROUND_HALF_UP));

        //雷达图数据
        JSONObject chart = new JSONObject(true);
        chart.put("性能指标得分", performance.setScale(1, BigDecimal.ROUND_HALF_UP));
        chart.put("经济指标得分", economy.setScale(1, BigDecimal.ROUND_HALF_UP));
        chart.put("审美指标得分", aestheticIndex.setScale(1, BigDecimal.ROUND_HALF_UP));
        chart.put("使用便捷性指标得分", ease.setScale(1, BigDecimal.ROUND_HALF_UP));
        chart.put("model", info.getModel());
        obj.put("chart", chart);

        //详细得分报表
        JSONObject report = new JSONObject(true);
        report.put("level", levelBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("noise", noiseBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("refri", refriBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("totalVol", totalVolBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("climate", climateBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("price", priceBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("warranty", warrantyBig.setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("profiling", new BigDecimal(ratingDTO.getProfiling()).setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("color", new BigDecimal(ratingDTO.getColor()).setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("interior", new BigDecimal(ratingDTO.getInterior()).setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("light", new BigDecimal(ratingDTO.getLight()).setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("ergonomics", new BigDecimal(ratingDTO.getErgonomics()).setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("storage", new BigDecimal(ratingDTO.getStorage()).setScale(1, BigDecimal.ROUND_HALF_UP));
        report.put("operation", new BigDecimal(ratingDTO.getOperation()).setScale(1, BigDecimal.ROUND_HALF_UP));
        obj.put("report", report);
        return obj;
    }


}
