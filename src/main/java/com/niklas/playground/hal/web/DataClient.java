package com.niklas.playground.hal.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.niklas.playground.hal.dto.Triple;
import com.niklas.playground.hal.dto.VariationSummary;
import com.niklas.playground.hal.web.dto.BrandResponse;
import com.niklas.playground.hal.web.dto.ProductTypeResponse;
import com.niklas.playground.hal.web.dto.SupplierResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.hint.TypeReference;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataClient {
    private final WebClient webClient;

    private static final ParameterizedTypeReference<CollectionModel<VariationSummary>> VARIATIONS =
            new ParameterizedTypeReference<>() {};
    private final UrlBasedViewResolver urlBasedViewResolver;

    public Mono<CollectionModel<VariationSummary>> getHalResponse() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/data").build())
                .retrieve()
                .bodyToMono(VARIATIONS);
    }

    public Flux<BrandResponse> getBrand(Mono<CollectionModel<VariationSummary>> response) {
        return response.flatMapMany(Flux::fromIterable)
                .map(variation -> variation.getRequiredLink("o:brand").getHref())
                .flatMap(url -> followLink(url, BrandResponse.class));
    }

    public Flux<SupplierResponse> getSupplier(Mono<CollectionModel<VariationSummary>> response) {
        return response.flatMapMany(Flux::fromIterable)
                .map(variation -> variation.getRequiredLink("o:retailer-related").getHref())
                .flatMap(url -> followLink(url, SupplierResponse.class));
    }

    public Flux<ProductTypeResponse> getProductType(Mono<CollectionModel<VariationSummary>> response) {
        return response.flatMapMany(Flux::fromIterable)
                .map(variation -> variation.getRequiredLink("o:attributes").getHref())
                .flatMap(url -> followLink(url, ProductTypeResponse.class));
    }

    public <T> Mono<T> followLink(String url, Class<T> clazz) {
        System.out.println(url);
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
        return Mono.zip(brand, supplierId, productType).map(t -> new Triple(t.getT1().getTitle(), t.getT2().getSupplier().getId(), t.getT3().getAttributes().getFirst().getValues().getFirst())).blockOptional();
    }
}
