package com.dslab.homework1.videoprocessingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class VideoProcessService {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskExecutor taskExecutor;

    public boolean encoding() {

        boolean exit=false;

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {

                //FFMPEG Bash Script Execution
                try {
                    Process process = Runtime.getRuntime().exec("./videoEncoder video.mp4 Storage/var/videofiles ");
                    //Sistemare

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return true;  //solo per non avere errori al momento del commit (da modificare!)
    }
}
