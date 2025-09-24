package com.niklas.playground.hal.web.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.niklas.playground.hal.web.constants.DataConstants;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@JsonRootName("attributes")
public class ProductTypeResponse {
    List<Attribute> attributes;

    public ProductTypeResponse(List<Attribute> attributes) {
        this.attributes = attributes.stream()
                .filter(attribute -> Objects.equals(attribute.getName(), DataConstants.PRODUCT_TYPE))
                .toList();

        if(this.attributes.isEmpty()) {
            throw new IllegalArgumentException("No 'ProductType' attribute found");
        }
    }

    @Data
    public static class Attribute {
        private String name;
        private List<String> values;
    }
}
