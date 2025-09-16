package com.niklas.playground.hal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
@JsonRootName("attributes")
public class ProductTypeResponse {
    List<Attribute> attributes;

    public ProductTypeResponse(List<Attribute> attributes) {
        this.attributes = attributes.stream()
                .filter(attribute -> Objects.equals(attribute.getName(), "Produkttyp"))
                .toList();
    }

    @Getter
    @AllArgsConstructor
    public static class Attribute {
        private String name;
        private List<String> values;
    }
}
