package com.api.price.pricecheck.com.api.price.pricecheck.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    private BigDecimal total;
    private BigDecimal totalTaxes;
}

