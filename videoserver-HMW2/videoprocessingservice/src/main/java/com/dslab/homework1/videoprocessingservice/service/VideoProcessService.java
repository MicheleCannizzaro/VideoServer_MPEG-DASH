package com.dslab.homework1.videoprocessingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Service
public class VideoProcessService {

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

    @Async
    public CompletableFuture<Integer> encode(Integer id) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder();
        builder.command("./videoEncoder", "/Storage/var/video/" + id + "/video.mp4", "/Storage/var/videofiles/" + id + "/");
        Process process = builder.start();
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();

        return CompletableFuture.completedFuture(exitCode);
    }
}
