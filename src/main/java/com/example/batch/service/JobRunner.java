package com.example.batch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class JobRunner implements CommandLineRunner {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {
        long totalTimeElapsed = 0;
        for (String fileName : args) {
            log.info("Start processing file: " + fileName);
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(Map.of("fileName", new JobParameter(fileName))));
            log.info("Finish processing file: " + fileName);
            long timeElapsed = jobExecution.getEndTime().getTime() - jobExecution.getCreateTime().getTime();
            log.info("It takes up " + timeElapsed + "ms to process file " + fileName);
            totalTimeElapsed += timeElapsed;
        }
        log.info(args.length + " file(s) processed. Total time: " + totalTimeElapsed + "ms");
        System.exit(0);
    }
}
