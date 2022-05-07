package com.example.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

@Slf4j
public class JsonFileReader implements ItemReader<JsonNode> {

    private BufferedReader reader;

    private ObjectMapper objectMapper;

    private long timeElapsed;

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
        timeElapsed = 0;
    }

    @AfterStep
    public void report() {
        log.info("Reader takes up " + timeElapsed + "ms");
    }

    @Override
    public JsonNode read() throws Exception {
        long startTime = System.currentTimeMillis();
        String line = reader.readLine();
        JsonNode jsonNode = line == null ? null : objectMapper.readTree(line);
        timeElapsed += System.currentTimeMillis() - startTime;
        return jsonNode;
    }
}
