package com.lryepoch.dao;

import com.lryepoch.entity.dto.ProductListDTO;
import com.lryepoch.entity.vo.InfoFilterVO;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/11/11 10:35
 * @description TODO
 */
public interface ReptileQueryMapper {
    List<ProductListDTO> getReptileProductList(InfoFilterVO infoFilterVO);
}
