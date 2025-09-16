package com.niklas.playground.hal.web.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.niklas.playground.hal.web.constants.DataConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
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

    @Getter
    @AllArgsConstructor
    public static class Attribute {
        private String name;
        private List<String> values;
    }
}
