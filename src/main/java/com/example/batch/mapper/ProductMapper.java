package com.example.batch.mapper;

import com.example.batch.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    void insertBatch(List<? extends Product> products);

}
