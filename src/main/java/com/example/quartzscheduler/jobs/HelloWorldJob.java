package com.example.quartzscheduler.jobs;

import com.example.quartzscheduler.info.TimerInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        LOG.info("Hello World");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
//        jobDataMap.put("Say","Hello");
        TimerInfo timerInfo = (TimerInfo) jobDataMap.get(HelloWorldJob.class.getSimpleName());
        LOG.info("Remaining fire count is '{}'",timerInfo.getRemainingFireCount());
//        String say = jobDataMap.getString("Say");
//        LOG.info(say);
    }
}
