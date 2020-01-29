package com.dslab.spout.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
public class KafkaSender implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String mainTopic;

    static int num_file=0;

    //@PostConstruct
    @Override
    public void run(String...args) throws FileNotFoundException, InterruptedException {

        Scanner fileScanner;
        File file;

        while(true){

            file=new File("/stats/stats_logs/stats_"+num_file+".txt");
            if(!file.exists()) continue;
            fileScanner= new Scanner(file);
            //int lineID = 0;

            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                String stat;
                //lineID++;
                if(line.contains("Com_delete") || line.contains("Com_insert") || line.contains("Com_select") || line.contains("Com_update")){
                    stat=line.replace("\t","|");
                    //System.out.println(stat);    //kafka
                    kafkaTemplate.send(mainTopic, num_file+"|stat1|"+stat);
                }else if(line.contains("Queries/Uptime")){
                    stat=("Queriespersec|").concat(fileScanner.nextLine());
                    kafkaTemplate.send(mainTopic,num_file+"|stat4|"+stat);
                }

            }
            num_file+=1;
            System.out.println("---------------------------"+ num_file);
            Thread.sleep(10000);

        }

    }

}
