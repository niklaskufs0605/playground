package com.niklas.playground.hal.web.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonRootName("supplier")
public class SupplierResponse {
    private Supplier supplier;

    @Getter
    @AllArgsConstructor
    public static class Supplier {
        private String id;
    }
}
