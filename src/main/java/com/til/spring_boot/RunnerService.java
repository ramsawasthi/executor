package com.til.spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@ComponentScan({"com.til"})
@Service
public class RunnerService {
   @Autowired
   @Qualifier("fixedThreadPool")
    ExecutorService executorService;


    public <T> Future<T> executeWithResult(Callable<T> callable) {
        return executorService.submit(callable);
    }

    public void shutdown() {
        System.out.println("Going for Shutdown....");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
