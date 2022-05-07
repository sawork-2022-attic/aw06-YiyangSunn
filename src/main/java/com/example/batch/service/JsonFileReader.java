package com.example.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

@Slf4j
public class JsonFileReader implements ItemReader<JsonNode> {

    private BufferedReader reader;

    private ObjectMapper objectMapper;

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
    }

    @Override
    public JsonNode read() throws Exception {
        String line = reader.readLine();
        return line == null ? null : objectMapper.readTree(line);
    }
}
