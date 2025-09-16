package com.niklas.playground.hal.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class SupplierResponse {
    private Supplier supplier;

    @Getter
    @AllArgsConstructor
    public static class Supplier {
        private String id;
    }
}
