package com.lryepoch.service;

import com.alibaba.fastjson.JSONObject;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.entity.vo.InfoFilterVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lryepoch
 * @date 2020/10/13 19:04
 * @description TODO 产品查询服务
 */
public interface QueryService {
    boolean judgeIfExist(String model);

    CommonResult getFilter();

    CommonResult getProductList(InfoFilterVO infoFilterVO, HttpServletRequest request);

    CommonResult getProductDetail(int id, HttpServletRequest request);

    CommonResult getProductTotal(InfoFilterVO infoFilterVO);

    JSONObject getProductContrast(Integer[] ids);

    CommonResult getReptileProductList(InfoFilterVO infoFilterVO);

    CommonResult getReptileDetails(int id);
}
