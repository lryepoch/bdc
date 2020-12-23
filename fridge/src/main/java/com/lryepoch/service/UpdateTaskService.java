package com.lryepoch.service;

/**
 * @author lryepoch
 * @date 2020/11/12 9:24
 * @description TODO 数据更新任务服务
 */
public interface UpdateTaskService {
    /**
    * @description
    * @author lryepoch 正式价格表从爬虫表周更新价格
    * @date 2020/11/12 9:27
    *
    */
    void updatePriceByWeek();

    /**
    * @description 缓存中设置上新价格
    * @author lryepoch
    * @date 2020/11/12 9:27
    *
    */
    void setReminderMach();
}
