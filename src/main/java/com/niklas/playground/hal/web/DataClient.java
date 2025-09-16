package com.niklas.playground.hal.web;

import com.niklas.playground.hal.dto.Triple;
import com.niklas.playground.hal.dto.VariationSummary;
import com.niklas.playground.hal.web.constants.DataConstants;
import com.niklas.playground.hal.web.dto.BrandResponse;
import com.niklas.playground.hal.web.dto.ProductTypeResponse;
import com.niklas.playground.hal.web.dto.SupplierResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataClient {
    private final WebClient webClient;

    private static final ParameterizedTypeReference<CollectionModel<VariationSummary>> VARIATIONS =
            new ParameterizedTypeReference<>() {};

    private Mono<CollectionModel<VariationSummary>> getHalResponse() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/data").build())
                .retrieve()
                .bodyToMono(VARIATIONS);
    }

    public Flux<BrandResponse> getBrand(Mono<CollectionModel<VariationSummary>> response) {
        return response.flatMapMany(Flux::fromIterable)
                .map(variation -> variation.getRequiredLink(DataConstants.BRAND_REFERENCE).getHref())
                .flatMap(url -> followLink(url, BrandResponse.class));
    }

    public Flux<SupplierResponse> getSupplier(Mono<CollectionModel<VariationSummary>> response) {
        return response.flatMapMany(Flux::fromIterable)
                .map(variation -> variation.getRequiredLink(DataConstants.RETAILER_RELATED_REFERENCE).getHref())
                .flatMap(url -> followLink(url, SupplierResponse.class));
    }

    public Flux<ProductTypeResponse> getProductType(Mono<CollectionModel<VariationSummary>> response) {
        return response.flatMapMany(Flux::fromIterable)
                .map(variation -> variation.getRequiredLink(DataConstants.ATTRIBUTES_REFERENCE).getHref())
                .flatMap(url -> followLink(url, ProductTypeResponse.class));
    }

    private <T> Mono<T> followLink(String url, Class<T> clazz) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(clazz);
    }

    public Optional<Triple> getTripleIdentifiers(){
        var response = getHalResponse();
        var brand = getBrand(response).single();
        var supplierId = getSupplier(response).single();
        var productType = getProductType(response).single();
        return Mono.zip(brand, supplierId, productType)
                .map(t ->
                        new Triple(t.getT1().getTitle(), t.getT2().getSupplier().getId(), t.getT3().getAttributes().getFirst().getValues().getFirst()))
                .blockOptional();
    }
}
