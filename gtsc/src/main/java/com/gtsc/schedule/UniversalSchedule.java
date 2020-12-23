package com.gtsc.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/12/22 13:58
 * @description TODO
 */
@Component
public class UniversalSchedule {

    @Scheduled(cron = "0 0 2 * * ?")
    public void insert() {
        System.out.println("^^^");
    }
}
