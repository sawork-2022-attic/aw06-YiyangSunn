package com.example.batch.service;

import com.example.batch.model.Product;
import com.example.batch.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ProductWriter implements ItemWriter<Product> {

    private List<Thread> workers;

    private BlockingQueue<List<? extends Product>> queue;

    private final ProductMapper productMapper;

    private final int workerCount;

    private final int queueSize;

    public ProductWriter(ProductMapper productMapper, int workerCount, int queueSize) {
        this.productMapper = productMapper;
        this.queueSize = queueSize;
        this.workerCount = workerCount;
    }

    @BeforeStep
    public void spawn() {
        queue = new LinkedBlockingQueue<>(queueSize);
        workers = new ArrayList<>();
        for (int i = 0; i < workerCount; ++i) {
            Thread w = new Thread(new Worker());
            w.start();
            workers.add(w);
        }
    }

    @AfterStep
    public void flush() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < workerCount; ++i) {
            queue.put(new ArrayList<>());
        }
        for (Thread w : workers) {
            w.join();
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        log.info("Waiting additional " + timeElapsed + "ms to flush the blocking queue.");
    }

    @Override
    public void write(List<? extends Product> list) throws InterruptedException {
        if (list.size() > 0) {
            queue.put(list);
        }
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    List<? extends Product> list = queue.take();
                    if (list.size() == 0) {
                        break;
                    } else {
                        productMapper.insertBatch(list);
                    }
                } catch (InterruptedException e) {
                    log.error("Unexpected Exception: " + e.getMessage());
                }
            }
        }
    }
}
