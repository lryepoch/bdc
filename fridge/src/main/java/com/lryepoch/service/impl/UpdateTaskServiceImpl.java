package com.lryepoch.service.impl;

import com.lryepoch.config.ReminderCache;
import com.lryepoch.dao.PriceQueryMapper;
import com.lryepoch.dao.ReminderMapper;
import com.lryepoch.entity.product.ProductPrice;
import com.lryepoch.entity.product.ProductReminder;
import com.lryepoch.service.UpdateTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author lryepoch
 * @date 2020/11/12 9:24
 * @description TODO 数据更新任务服务
 */
@Service
public class UpdateTaskServiceImpl implements UpdateTaskService {
    @Autowired
    private PriceQueryMapper priceQueryMapper;
    @Autowired
    private ReminderMapper reminderMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @description 正式价格表从爬虫表周更新价格
     * @author lryepoch
     * @date 2020/11/12 9:30
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePriceByWeek() {
        //这里限制了取出的条数为1000，循环去取，防止插入时foreach的sql长度太长超过max_allowed_packet导致插入报错
        List<ProductPrice> priceList = priceQueryMapper.selectMatchedPriceFromReptile();
        for (int i = 0; i < priceList.size(); ) {
            int j = i + 1000;
            if (j > priceList.size()) {
                j = priceList.size();
            }
            List<ProductPrice> tempList = priceList.subList(i, j);
            if (tempList.size() > 0) {
                tempList.forEach(p -> p.setUpdateTime(Timestamp.valueOf(LocalDateTime.now())));

                //将爬虫表中的数据数据取出，更新到正式价格表中
                priceQueryMapper.insertUpdatePrice(tempList);
                if (j == priceList.size()) {
                    break;
                }
            }
            i = j + 1;
        }

        //将爬虫表中的对应价格标记为已删除
        priceQueryMapper.deleteReptilePriceByWeekUpdate();

        logger.warn("【价格更新任务】" + LocalDateTime.now() + "更新了" + priceList.size() + "条价格");
    }

    /**
    * @description 缓存中设置上新价格的机型
    * @author lryepoch
    * @date 2020/11/12 10:06
    *
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setReminderMach() {
        //获取爬取价格对应的冰箱id
        List<Integer> remindList = priceQueryMapper.selectMatchIdFromReptile();
        HashSet<Integer> set = new HashSet<>(remindList);

        //清空所有用户缓存
        ReminderCache.map.clear();
        reminderMapper.clearReminder();

        //设置新一周的上新冰箱id
        ReminderCache.map.put("reminderSet", set);
        List<ProductReminder> rList = new ArrayList<>();
        for (Integer r:set){
            ProductReminder p = new ProductReminder();
            p.setPid(r);
            p.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            rList.add(p);
        }
        if (rList.size()>0){
            reminderMapper.insertReminder(rList);
        }
        logger.info(set.toString());
    }
}
