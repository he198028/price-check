package com.api.price.pricecheck.controller;

import com.api.price.pricecheck.com.api.price.pricecheck.domain.FlightData;
import com.api.price.pricecheck.com.api.price.pricecheck.domain.FlightResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PriceController {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger LOGGER = LoggerFactory.getLogger("PriceController");

    @Value("${flight-api-url}")
    private String flightApiUri;

    @Value("${number-of-top-offers}")
    private int numberOfTopOffers;

    @GetMapping(value = "/getBestPrice")
    @ResponseBody
    public FlightResponse getBestPrice(@RequestParam(value = "origin") String origin,
                                       @RequestParam(value = "destination") String destination,
                                       @RequestParam(value = "departureDate") String date,
                                       @RequestParam(value = "max") int max) throws Exception {
        try {
            FORMATTER.parse(date);
        } catch (Exception e) {
            LOGGER.error("Error date format for departure date: ", e);
            throw e;
        }

        try {
            String apiResponse = getPricesFromApi(origin, destination, date, max);
            FlightResponse response = JSON_MAPPER.readValue(apiResponse,
                    new TypeReference<FlightResponse>() { });
            response.getData();
            getCheapestOffer(response, numberOfTopOffers);
            return response;
        } catch (Exception e) {
            LOGGER.error("Error getting flight response: ", e);
            throw new IOException(e);
        }
    }

    void getCheapestOffer(FlightResponse flights, int offerLimit) {
        List<FlightData> flightData = flights.getData();
        if (flightData.size() <= numberOfTopOffers) {
            return;
        }

        Collections.sort(flightData, new Comparator<FlightData>() {

            @Override
            public int compare(FlightData f1, FlightData f2) {
                return f1.getOfferItems().get(0).getPrice().getTotal().compareTo(f2.getOfferItems().get(0).getPrice().getTotal());
            }

        });

        List<FlightData> newFlightData = flightData.stream().limit(offerLimit).collect(Collectors.toList());
        flights.setData(newFlightData);
    }

    String getPricesFromApi(String origin, String destination, String date, int max) {
        if (origin==null || destination==null || date==null) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        RestTemplate template = new RestTemplate();
        String url = flightApiUri + "?origin=" + origin + "&destination=" + destination + "&departureDate=" + date + "&max=" + max;
        String s = template.getForObject(url, String.class);
        return s;
    }
}
