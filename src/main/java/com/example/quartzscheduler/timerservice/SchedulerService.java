package com.example.quartzscheduler.timerservice;

import com.example.quartzscheduler.info.TimerInfo;
import com.example.quartzscheduler.util.TimeUtil;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    private final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);
    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(final Class jobClass, final TimerInfo info) {
        final JobDetail jobDetail = TimeUtil.buildJobDetail(jobClass, info);
        final Trigger trigger = TimeUtil.buildTrigger(jobClass, info);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public List<TimerInfo> showListTimer() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup()).stream().map(jobKey -> {
                        try {
                            final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            LOG.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public TimerInfo getRunningTimer(String timerId) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                return null;
            }
            return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public Boolean deleteTimer(String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public void updateTimer(final String timerId, final TimerInfo info) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                LOG.error("Cannot find job detail");
                return;
            }

            jobDetail.getJobDataMap().put(timerId, info);
            scheduler.addJob(jobDetail, true, true);
        } catch (SchedulerException e) {
            LOG.info(e.getMessage(), e);
        }

    }

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
//            LOG.info("End schedule");
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
