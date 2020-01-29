package com.dslab.spout;

import com.dslab.spout.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class SpoutApplication {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        SpringApplication.run(SpoutApplication.class, args);

    }

}
