package com.dslab.homework1.videoprocessingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VideoProcessService {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskExecutor taskExecutor;

    public boolean encode(Integer id){

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {

                //FFMPEG Bash Script Execution
                try {
                    Process process = Runtime.getRuntime().exec("./videoEncoder /Storage/var/video/" + id + "video.mp4 /Storage/var/videofiles/" + id + "/");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return true;
    }
}
