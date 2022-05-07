package com.example.batch.service;

import com.example.batch.model.Product;
import com.example.batch.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class ProductWriter implements ItemWriter<Product> {

    private final ProductMapper productMapper;

    public ProductWriter(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void write(List<? extends Product> list) {
        productMapper.insertBatch(list);
    }
}
