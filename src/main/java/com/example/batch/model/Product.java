package com.example.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Long id;

    private String asin;

    private String main_cat;

    private String title;

    private List<String> also_view;

    private List<String> imageURLHighRes;
}
