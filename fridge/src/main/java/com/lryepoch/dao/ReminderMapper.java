package com.lryepoch.dao;

import com.lryepoch.entity.product.ProductReminder;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/11/11 8:49
 * @description TODO
 */
public interface ReminderMapper {


    List<ProductReminder> getReminder();

    void clearReminder();

    void insertReminder(List<ProductReminder> list);
}
