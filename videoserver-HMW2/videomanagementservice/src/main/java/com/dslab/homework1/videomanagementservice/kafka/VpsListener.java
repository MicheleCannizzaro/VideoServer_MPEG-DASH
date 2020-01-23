package com.dslab.homework1.videomanagementservice.kafka;

import com.dslab.homework1.videomanagementservice.entity.Video;
import com.dslab.homework1.videomanagementservice.entity.VideoRepository;
import com.dslab.homework1.videomanagementservice.entity.VideoStatus;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class VpsListener {

    @Autowired
    VideoRepository Vrepository;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String mainTopic;

    @KafkaListener(topics="${KAFKA_MAIN_TOPIC}")
    public void listen(String message) throws IOException {
        System.out.println("Received message: " + message);

        String[] messageParts = message.split("\\|");

        if (messageParts[0].equals("Processed")) {
            Integer vid =Integer.valueOf(messageParts[1]);
            Video v=Vrepository.findById(vid).get();
            v.setState(VideoStatus.AVAILABLE);
            Vrepository.save(v);
        }


        if (messageParts[0].equals("ProcessingFailed")) {
            Integer vid = Integer.valueOf(messageParts[1]);
            Video v=Vrepository.findById(vid).get();
            v.setState(VideoStatus.NOT_AVAILABLE);
            Vrepository.save(v);
            FileUtils.deleteDirectory(new File("/Storage/var/video/"+vid));
            FileUtils.deleteDirectory(new File("/Storage/var/videofiles/"+vid));
        }

    }

}
