package com.api.price.pricecheck.controller;

import com.api.price.pricecheck.com.api.price.pricecheck.domain.FlightData;
import com.api.price.pricecheck.com.api.price.pricecheck.domain.FlightResponse;
import com.api.price.pricecheck.com.api.price.pricecheck.domain.OfferItem;
import com.api.price.pricecheck.com.api.price.pricecheck.domain.Price;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class PriceControllerTest {
    @Test
    public void testGetCheapestOffer() {
        FlightResponse response = new FlightResponse();
        response.setData(new ArrayList<>());

        FlightData data = new FlightData();
        data.setOfferItems(new ArrayList<>());
        data.getOfferItems().add(new OfferItem());
        Price price = new Price();
        price.setTotal(new BigDecimal(20));
        data.getOfferItems().get(0).setPrice(price);
        response.getData().add(data);

        data = new FlightData();
        data.setOfferItems(new ArrayList<>());
        data.getOfferItems().add(new OfferItem());
        price = new Price();
        price.setTotal(new BigDecimal(10));
        data.getOfferItems().get(0).setPrice(price);
        response.getData().add(data);

        PriceController controller = new PriceController();
        controller.getCheapestOffer(response, 1);

        assertTrue(response.getData().size()==1);
        assertTrue(response.getData().get(0).getOfferItems().get(0).getPrice().getTotal().compareTo(new BigDecimal(10))==0);
    }
}
