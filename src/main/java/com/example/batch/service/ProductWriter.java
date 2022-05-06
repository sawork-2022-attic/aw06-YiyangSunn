package com.example.batch.service;

import com.example.batch.model.Product;
import com.example.batch.mapper.ProductMapper;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class ProductWriter implements ItemWriter<Product> {

    private final int batchSize;

    private final List<Product> buffer;

    private final ProductMapper productMapper;

    public ProductWriter(ProductMapper productMapper, int batchSize) {
        this.batchSize = batchSize;
        this.buffer = new ArrayList<>();
        this.productMapper = productMapper;
    }

    @AfterStep
    public void flush() {
        if (buffer.size() > 0) {
            productMapper.insertBatch(buffer);
        }
    }

    @Override
    public void write(List<? extends Product> list) throws Exception {
        int n = list.size();
        while (n >= batchSize) {
            productMapper.insertBatch(list.subList(n - batchSize, n));
            n -= batchSize;
        }
        int m = buffer.size();
        if (n + m >= batchSize) {
            int k = batchSize - m;
            buffer.addAll(list.subList(n - k, n));
            productMapper.insertBatch(buffer);
            buffer.clear();
            n -= k;
        }
        if (n > 0) {
            buffer.addAll(list.subList(0, n));
        }
    }
}
