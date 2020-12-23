package com.lryepoch.task;

import com.lryepoch.service.UpdateTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/11/12 9:22
 * @description TODO 定期每周抽取爬虫价格表中与正式数据匹配的价格进入正式表
 */
@Component
public class UpdateReptilePriceByWeek {
    @Autowired
    private UpdateTaskService updateTaskService;

    /**
    * @description 每周日晚上8点执行，根据爬取数据拉取最新价格
    * @author lryepoch
    * @date 2020/11/12 9:26
    *
    */
    @Scheduled(cron = "0 0 20 ? * SUN")
    public void updatePrice(){
        updateTaskService.updatePriceByWeek();
        updateTaskService.setReminderMach();
    }
}
