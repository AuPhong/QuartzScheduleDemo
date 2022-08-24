package com.example.quartzscheduler.timerservice;

import com.example.quartzscheduler.info.TimerInfo;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class SimpleTriggerListener implements TriggerListener {
    private final SchedulerService schedulerService;

    public SimpleTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
//        System.out.println("Trigger function is called.");
        final String timerId = trigger.getKey().getName();
        final JobDetail jobDetail = context.getJobDetail();
        final TimerInfo timerInfo = (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        if (!timerInfo.isRunForever()){
            int remainingFireCount = timerInfo.getRemainingFireCount();
            if (remainingFireCount == 0){
                return;
            }
            timerInfo.setRemainingFireCount(remainingFireCount - 1);
        }
        schedulerService.updateTimer(timerId, timerInfo);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
