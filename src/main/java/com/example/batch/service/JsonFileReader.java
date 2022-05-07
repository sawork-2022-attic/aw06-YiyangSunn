package com.example.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class JsonFileReader implements ItemReader<JsonNode> {

    private BufferedReader reader;

    private ObjectMapper objectMapper;

    private BlockingQueue<JsonNode> queue;

    private final int workerCount;

    private final int queueSize;

    public JsonFileReader(int queueSize, int workerCount) {
        this.queueSize = queueSize;
        this.workerCount = workerCount;
    }

    @BeforeStep
    public void prepare(StepExecution stepExecution) throws Exception {
        String fileName = stepExecution.getJobParameters().getString("fileName");
        if (fileName == null) {
            throw new IllegalArgumentException("missing 'fileName' parameter in JobParameters");
        }
        InputStream inputStream;
        try {
            inputStream = new ClassPathResource(fileName).getInputStream();
        } catch (FileNotFoundException e) {
            inputStream = new FileInputStream(fileName);
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
        objectMapper = new ObjectMapper();
        queue = new LinkedBlockingQueue<>(queueSize);
        for (int i = 0; i < workerCount; ++i) {
            Thread w = new Thread(new Worker());
            w.start();
        }
    }

    @Override
    public JsonNode read() throws InterruptedException {
        JsonNode node = queue.take();
        return node.isNull() ? null : node;
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        queue.put(objectMapper.readTree("null"));
                        break;
                    } else {
                        queue.put(objectMapper.readTree(line));
                    }
                } catch (Exception e) {
                    log.error("Unexpected Exception: " + e.getMessage());
                }
            }
        }
    }
}
