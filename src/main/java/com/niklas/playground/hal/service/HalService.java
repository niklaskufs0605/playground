package com.niklas.playground.hal.service;

import com.niklas.playground.hal.dto.Triple;
import com.niklas.playground.hal.web.DataClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HalService {
    private final DataClient dataClient;

    public Triple fetchData() {
        return dataClient.getTripleIdentifiers().orElseThrow();
    }
}
