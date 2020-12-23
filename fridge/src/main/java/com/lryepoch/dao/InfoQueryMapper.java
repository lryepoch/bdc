package com.lryepoch.dao;

import com.lryepoch.entity.dto.ProductListDTO;
import com.lryepoch.entity.product.ProductInfo;
import com.lryepoch.entity.vo.InfoFilterVO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/10 18:44
 * @description TODO
 */
public interface InfoQueryMapper {
    void deleteProductInfo(List<Integer> ids);

    ProductInfo findOneProductInfo(String model);

    LinkedList<ProductListDTO> getProductList(InfoFilterVO infoFilterVO);
}
