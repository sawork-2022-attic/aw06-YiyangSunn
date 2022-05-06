package com.example.batch.service;

import com.example.batch.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class ProductProcessor implements ItemProcessor<JsonNode, Product> {

    private ObjectMapper objectMapper;

    @BeforeStep
    public void prepare() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Product process(JsonNode jsonNode) throws Exception {
        Product product = objectMapper.treeToValue(jsonNode, Product.class);
        // we'll use asin as primary key
        String asin = product.getAsin();
        if (asin == null || asin.isBlank()) {
            return null;
        }
        // broken title can not be displayed, so we omit it
        String title = product.getTitle();
        if (title == null || title.isBlank() || title.charAt(0) == '<') {
            return null;
        }
        List<String> images = product.getImageURLHighRes();
        // we need at least one image to display
        if (images.size() == 0) {
            return null;
        } else if (images.size() > 5) {
            // we only keep the first 5 URLs
            images = images.subList(0, 5);
            product.setImageURLHighRes(images);
        }
        List<String> alsoViews = product.getAlso_view();
        if (alsoViews.size() > 5) {
            // truncate it
            alsoViews = alsoViews.subList(0, 5);
            product.setAlso_view(alsoViews);
        }
        // assume it's ok
        return product;
    }
}
