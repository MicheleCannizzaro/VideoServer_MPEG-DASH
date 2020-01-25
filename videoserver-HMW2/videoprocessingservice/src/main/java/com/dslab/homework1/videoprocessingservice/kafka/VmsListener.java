package com.dslab.homework1.videoprocessingservice.kafka;


import com.dslab.homework1.videoprocessingservice.service.VideoProcessService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class VmsListener {

    @Autowired
    VideoProcessService video_process_service;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String mainTopic;

    @KafkaListener(topics="${KAFKA_MAIN_TOPIC}")
    public void listen(String message) throws IOException, InterruptedException, ExecutionException {
        System.out.println("Received message: " + message);

        String[] messageParts = message.split("\\|");

        if (messageParts[0].equals("Process")) {

            CompletableFuture.supplyAsync(() -> {
                int exitStatus=1;
                try {
                    exitStatus=video_process_service.encode(messageParts[1]);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                return exitStatus;
            }).exceptionally(ex -> {kafkaTemplate.send(mainTopic, "ProcessingException|" + messageParts[1]);
                return null;
            }).thenAccept(res -> {
                if (res==0) {
                    kafkaTemplate.send(mainTopic, "Processed|" + messageParts[1]);
                }else {
                    kafkaTemplate.send(mainTopic, "ProcessingFailed|" + messageParts[1]);
                }
            });
        }
    }
}
