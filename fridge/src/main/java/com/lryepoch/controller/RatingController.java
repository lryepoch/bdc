package com.lryepoch.controller;

import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.config.entity.ResultEnum;
import com.lryepoch.dao.RatingQueryMapper;
import com.lryepoch.dao.jpa.ProductInfoMapper;
import com.lryepoch.dao.jpa.RatingJpaMapper;
import com.lryepoch.entity.product.RateWeight;
import com.lryepoch.entity.vo.ProductRatingVO;
import com.lryepoch.service.RateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @author lryepoch
 * @date 2020/10/9 16:18
 * @description TODO
 */
@Api(description = "评分控制器")
@RestController
@RequestMapping(value = "/rating")
public class RatingController {
    @Autowired
    private RateService rateService;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private RatingJpaMapper ratingJpaMapper;
    @Resource
    private RatingQueryMapper ratingQueryMapper;

    @ApiOperation(value = "设置计算得分的权重")
    @PostMapping(value = "/setWeight")
    public CommonResult setRateWeight(@RequestBody RateWeight rateWeight) {
        rateService.setRateWeight(rateWeight);
        return CommonResult.success(ResultEnum.SUCCESS.getCode(), "权重修改成功");
    }

    @ApiOperation(value = "获取得分的权重")
    @GetMapping(value = "/getWeight")
    public CommonResult getWeight() {
        return CommonResult.success(rateService.getRateWeight());
    }

    /**
    * 对产品评分，默认一个型号只评一次
    */
    @ApiOperation(value = "用户对产品评分", notes = "uid是用户id，productRatingVO id是mid")
    @PostMapping(value = "/productRate")
    public CommonResult rateForModel(@RequestBody ProductRatingVO productRatingVO) throws IllegalAccessException {
        //检测该机型是否存在
        if (productInfoMapper.existsById(productRatingVO.getId())) {
            //此处默认一个用户只能对一个机型评价一次，若可修改评分注释此处即可
            if (ratingJpaMapper.existsByUidAndMid(1, productRatingVO.getId())) {
                return CommonResult.fail(ResultEnum.ERR.getCode(), "用户已对该机型评价过了");
            }
            //判断评分分数是否合法，利用反射
            Field[] fields = ProductRatingVO.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType() == Double.class) {
                    field.setAccessible(true);
                    if (((Double)field.get(productRatingVO)) > 10 || ((Double)field.get(productRatingVO)) < 0) {
                        return CommonResult.fail(ResultEnum.ERR.getCode(), "【" + field.getName() + "】评分需要在0-10范围内");
                    }
                }
            }
            //通过id、uid插入对机型的评分
            ratingQueryMapper.insertRatingById(1,productRatingVO);
            return CommonResult.success(ResultEnum.SUCCESS.getCode(),"评价成功");
        }
        return CommonResult.fail(ResultEnum.ERR.getCode(), "该机型不存在");
    }
}
