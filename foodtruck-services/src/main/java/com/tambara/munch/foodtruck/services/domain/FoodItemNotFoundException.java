package com.tambara.munch.foodtruck.services.domain;

public class FoodItemNotFoundException extends RuntimeException{
    public FoodItemNotFoundException(String message) {
        super(message);
    }
}
