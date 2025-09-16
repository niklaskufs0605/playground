package com.niklas.playground.hal.controller;

import com.niklas.playground.hal.dto.Triple;
import com.niklas.playground.hal.service.HalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HalController {

    private final HalService halService;

    @GetMapping("/test")
    public ResponseEntity<Triple> getData() {
        var triple = halService.fetchData();
        return ResponseEntity.ok(triple);
    }
}
