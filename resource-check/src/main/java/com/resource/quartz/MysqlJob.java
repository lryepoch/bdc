package com.resource.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author lryepoch
 * @date 2020/12/22 15:42
 * @description TODO
 */
@Component
public class MysqlJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("mysql");
    }
}
