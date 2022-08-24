package com.example.quartzscheduler.playground;

import com.example.quartzscheduler.info.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/timer")
public class PlayGroundController {
    private PlayGroundService playGroundService;

    @Autowired
    public PlayGroundController(PlayGroundService playGroundService) {
        this.playGroundService = playGroundService;
    }

    @PostMapping("/runhelloworld")
    public void runhelloworld(){
        playGroundService.runHelloWorld();
    }
    @GetMapping
    public List<TimerInfo> showListTimer(){
        return playGroundService.showListTimer();
    }
    @GetMapping("/{timerId}")
    public TimerInfo getRunningTimer(@PathVariable String timerId){
        return playGroundService.getRunningTimer(timerId);
    }
    @DeleteMapping("/{timerId}")
    public Boolean deleteTimer(@PathVariable String timerId){
        return playGroundService.deleteTimer(timerId);
    }
}
