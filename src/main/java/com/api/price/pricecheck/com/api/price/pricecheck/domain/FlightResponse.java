package com.api.price.pricecheck.com.api.price.pricecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightResponse {
    private Meta meta;
    private List<FlightData> data;
}
