package com.niklas.playground.hal.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "o:variation", collectionRelation = "o:variation")
public class VariationSummary extends RepresentationModel<VariationSummary> {
}
