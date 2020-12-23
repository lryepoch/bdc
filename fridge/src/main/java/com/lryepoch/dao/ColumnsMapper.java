package com.lryepoch.dao;

import com.lryepoch.entity.dto.columns.ColumnDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/30 9:48
 * @description TODO
 */
public interface ColumnsMapper {

    List<ColumnDTO> getAimedList(String type);

    /*获取excel中price表需要的字段*/
    List<ColumnDTO> getExcelPriceList();

    /*获取指定属性的所有值*/
    List<String> getFilterValue(@Param("columnName") String columnName, @Param("tableName") String tableName);

    /*获取excel中info表需要的字段*/
    List<ColumnDTO> getExcelInfoList();
}
