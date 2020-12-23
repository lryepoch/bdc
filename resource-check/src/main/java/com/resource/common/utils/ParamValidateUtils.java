package com.resource.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BindingResult;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lryepoch
 * @date 2020/12/14 18:07
 * @description TODO 工具类
 */
public class ParamValidateUtils {

    /**
    * @description 检验@Valid注解修饰
    * @author lryepoch
    * @date 2020/12/14 18:17
    *
    */
    public static void checkParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
//            throw new SystemException();
        }
    }

    /**
     * @author 260408
     * @date 2020/11/4 11:29
     * @description 配合BeanUtils设置对于源对象中空的元素不进行复制，避免了目标对象某属性原本不空，赋值后为空
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
