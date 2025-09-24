package com.niklas.playground.hal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Triple {
    private String brandId;
    private String supplierId;
    private String productType;
}
