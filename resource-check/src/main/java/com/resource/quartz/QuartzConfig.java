package com.resource.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * @author lryepoch
 * @date 2020/12/22 15:38
 * @description TODO
 */
@Component
public class QuartzConfig {

    @Bean
    public void config() throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        //任务1
        JobDetail jobDetail = newJob(MysqlJob.class)
                .withIdentity("emailjob", "group1").build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0 0/5 * * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);


        //任务2
        jobDetail = newJob(EmailJob.class)
                .withIdentity("EmailJob", "group2").build();

        trigger = newTrigger()
                .withIdentity("trigger2", "group2")
                .withSchedule(cronSchedule("0 30 08* * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
