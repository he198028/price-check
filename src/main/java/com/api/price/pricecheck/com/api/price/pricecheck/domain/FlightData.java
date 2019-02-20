package com.api.price.pricecheck.com.api.price.pricecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightData {
    private String type;
    private String id;
    private List<OfferItem> offerItems;
}
