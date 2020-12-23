package com.lryepoch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lryepoch.config.entity.PathEntity;
import com.lryepoch.dao.ColumnsMapper;
import com.lryepoch.entity.dto.columns.ColumnDTO;
import com.lryepoch.entity.product.ProductProfessionalParameters;
import com.lryepoch.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lryepoch
 * @date 2020/11/11 14:40
 * @description TODO
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Resource
    private ColumnsMapper columnsMapper;
    @Autowired
    private PathEntity pathEntity;

    /**
    * @description 填充专业参数中的各个组件的图片url
    * @author lryepoch
    * @date 2020/11/11 14:59
    *
    */
    @Override
    public JSONObject packingProfPictures(int id) {
        //获取专业参数表中为图片的字段，这里因为info表的图片字段并没有录入该表
        //（因为info表图片是多张，专业表也是多张，所以也是代码生成，没存数据库），所以是取巧，后期可以改成枚举
        List<String> list = columnsMapper.getAimedList("string")
                .stream().filter(columnDTO -> columnDTO.getColumnZh().contains("图片"))
                .sorted(Comparator.comparingInt(ColumnDTO::getId)).map(ColumnDTO::getColumn)
                .collect(Collectors.toList());

        JSONObject json = new JSONObject();
        Class clazz = ProductProfessionalParameters.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            String tempPath;
            if (list.contains(field.getName())){
                List<String> urlList = new ArrayList<>();
                //找到每个域的根地址
                tempPath = pathEntity.getPicturePath()+"/"+id+"/"+field.getName();
                File parentFile = new File(tempPath);
                File[] files;
                //如果该域的根地址存在，且其下有文件即默认其有上传对应的图片，并拼出图片请求地址
                if (parentFile.exists() && Objects.requireNonNull(files=parentFile.listFiles()).length>0){
                    for (File sonFile:files){
                        urlList.add("/picture/"+id+"/"+field.getName()+"/"+sonFile.getName());
                    }
                }
                json.put(field.getName(), urlList);
            }
        }
        return json;
    }
}
