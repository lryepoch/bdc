package com.lryepoch.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/9/29 17:20
 * @description TODO 实体转换器
 */
public class ConverterUtils {

    /**
     * @description 应用于单个对象PO转VO
     * @author lryepoch
     * @date 2020/9/29 17:25
     */
    @SuppressWarnings("unchecked")
    public static <T1, T2> T1 toVO(T2 t2, Class clazz) {
        Logger logger = LoggerFactory.getLogger("Converter");

        Object object = null;
        try {
            object = clazz.newInstance();
            PropertyUtils.copyProperties(object, t2);
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            logger.error("converter error");
        }
        return (T1) object;
    }

    /**
    * @description 应用于List PO转VO
    * @author lryepoch
    * @date 2020/9/29 17:44
    *
    */
    @SuppressWarnings("unchecked")
    public static <T1, T2> List<T1> toListVO(List<T2> list, Class clazz) {
        Logger logger = LoggerFactory.getLogger("Converter");

        List<T1> t1List = new ArrayList<>();
        for (T2 t2: list) {
            Object object=null;
            try {
                object = clazz.newInstance();
                PropertyUtils.copyProperties(object, t2);
            } catch (InvocationTargetException | NoSuchMethodException |IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                logger.error("converter error");
            }
            t1List.add((T1) object);
        }
        return t1List;
    }
}
