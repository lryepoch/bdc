package com.lryepoch.entity.dto.columns;

import com.lryepoch.dao.ColumnsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lryepoch
 * @date 2020/10/13 20:28
 * @description TODO 字段校验缓存类，拿到各个表需要校验的字段，并把是否必要、中文都放进其中
 */
@Service
public class ColumnCache {
    @Resource
    private ColumnsMapper columnsMapper;

    //本身还是字符串，只是数据类型各不同
    private List<String> stringList = new ArrayList<>();
    private List<String> intList = new ArrayList<>();
    private List<String> doubleList = new ArrayList<>();
    private List<String> timeList = new ArrayList<>();

    private Map<String, String> columnsZh = new HashMap<>();
    private Map<String, Integer> columnsFlag = new HashMap<>();

    /**
    * 是否中文
    */
    public Map<String, String> getColumnsZh(){
        return columnsZh;
    }

    /**
    * 是否必要
    */
    public Map<String, Integer> getColumnsFlag(){
        return columnsFlag;
    }

    public List<String> getStringList() {
        if (stringList != null && stringList.size() > 0) {
            return stringList;
        } else {
            setStringList();
            return stringList;
        }
    }

    private void setStringList() {
        List<ColumnDTO> columnDTOList = columnsMapper.getAimedList("string");
        for (ColumnDTO columnDTO : columnDTOList) {
            stringList.add(columnDTO.getColumn());
            columnsZh.put(columnDTO.getColumn(), columnDTO.getColumnZh());
            columnsFlag.put(columnDTO.getColumn(), columnDTO.getFlag());
        }
    }

    public List<String> getIntList() {
        if (intList != null && intList.size() > 0) {
            return intList;
        } else {
            setIntList();
            return intList;
        }
    }


    private void setIntList() {
        List<ColumnDTO> columnDTOList = columnsMapper.getAimedList("int");
        for (ColumnDTO columnDTO : columnDTOList) {
            intList.add(columnDTO.getColumn());
            columnsZh.put(columnDTO.getColumn(), columnDTO.getColumnZh());
            columnsFlag.put(columnDTO.getColumn(), columnDTO.getFlag());
        }
    }

    public List<String> getDoubleList() {
        if (doubleList != null && doubleList.size() > 0) {
            return doubleList;
        } else {
            setDoubleList();
            return doubleList;
        }
    }

    private void setDoubleList() {
        List<ColumnDTO> columnDTOList = columnsMapper.getAimedList("double");
        for (ColumnDTO columnDTO : columnDTOList) {
            doubleList.add(columnDTO.getColumn());
            columnsZh.put(columnDTO.getColumn(), columnDTO.getColumnZh());
            columnsFlag.put(columnDTO.getColumn(), columnDTO.getFlag());
        }
    }

    public List<String> getTimeList() {
        if (timeList != null && timeList.size() > 0) {
            return timeList;
        } else {
            setTimeList();
            return timeList;
        }
    }

    private void setTimeList() {
        List<ColumnDTO> columnDTOList = columnsMapper.getAimedList("time");
        for (ColumnDTO columnDTO : columnDTOList) {
            timeList.add(columnDTO.getColumn());
            columnsZh.put(columnDTO.getColumn(), columnDTO.getColumnZh());
            columnsFlag.put(columnDTO.getColumn(), columnDTO.getFlag());
        }
    }


}
