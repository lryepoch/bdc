package com.lryepoch.service;

import com.alibaba.fastjson.JSONObject;
import com.lryepoch.config.entity.CommonResult;
import com.lryepoch.entity.product.ProductInfo;
import com.lryepoch.entity.product.ProductProfessionalParameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/10 18:36
 * @description TODO
 */
public interface DataService {
    void deleteProduct(List<Integer> ids);

    void profInsertAndUpdate(ProductProfessionalParameters profParam);

    CommonResult infoAndPriceAddAndUpdate(JSONObject json);

    String judgeService(JSONObject json, Class clazz);

    CommonResult excelSaveInfo(InputStream inputStream, String fileType) throws IOException;

    CommonResult excelSavePrice(InputStream inputStream, String fileType) throws IOException;

    CommonResult ensureReptileData(List<Integer> ids);

    CommonResult updateReptileData(JSONObject json);
}
