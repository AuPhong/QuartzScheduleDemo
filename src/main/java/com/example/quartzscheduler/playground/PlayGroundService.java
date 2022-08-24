package com.example.quartzscheduler.playground;

import com.example.quartzscheduler.info.TimerInfo;
import com.example.quartzscheduler.jobs.HelloWorldJob;
import com.example.quartzscheduler.timerservice.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayGroundService {
    @Autowired
    SchedulerService schedulerService;

    public void runHelloWorld(){
        TimerInfo info = new TimerInfo();
        info.setTotalFireCount(5);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setCallbackData("My callback data");
        info.setRepeatIntervalMs(2000);
        info.setIntervalOffsetMs(2000);

        schedulerService.schedule(HelloWorldJob.class, info);
    }

    public List<TimerInfo> showListTimer(){
        return schedulerService.showListTimer();
    }

    public TimerInfo getRunningTimer(String timerId){
        return schedulerService.getRunningTimer(timerId);
    }

    public Boolean deleteTimer(String timerId){
        return schedulerService.deleteTimer(timerId);
    }
}
