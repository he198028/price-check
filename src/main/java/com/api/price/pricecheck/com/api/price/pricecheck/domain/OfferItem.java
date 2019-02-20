package com.api.price.pricecheck.com.api.price.pricecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferItem {
    private Price price;
    private Price pricePerAdult;
}
