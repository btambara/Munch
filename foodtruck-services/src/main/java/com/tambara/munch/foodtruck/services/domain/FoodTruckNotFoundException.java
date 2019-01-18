package com.tambara.munch.foodtruck.services.domain;

public class FoodTruckNotFoundException extends RuntimeException{
    public FoodTruckNotFoundException(String message) {
        super(message);
    }
}
