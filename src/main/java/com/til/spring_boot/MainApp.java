package com.til.spring_boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@SpringBootApplication
public class MainApp implements CommandLineRunner {
    @Autowired
    RunnerService runnerService;
    private static final Logger LOG = LoggerFactory
            .getLogger(
                    MainApp.class);



    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(MainApp.class, args);
        LOG.info("APPLICATION FINISHED");

    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");
        Map<String, String> m = new HashMap<String,String>();

        Callable<Map<String,String>> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            m.put(Thread.currentThread().getId()+"", Thread.currentThread().getName());
            System.out.println("Executing Task :" + Thread.currentThread().getName()) ;
            return m;
        };

        for (int i = 0 ; i < 10 ; i++){
            Future<Map<String,String>> future = runnerService.executeWithResult(callableTask);
            Map<String,String> result = null;
            try {
                result = future.get();
                System.out.println(result.toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        runnerService.shutdown();
    }


}
