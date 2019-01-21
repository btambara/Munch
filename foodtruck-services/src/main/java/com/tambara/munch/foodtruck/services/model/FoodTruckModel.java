package com.tambara.munch.foodtruck.services.model;

import com.tambara.munch.foodtruck.services.domain.FoodItem;
import com.tambara.munch.foodtruck.services.domain.FoodTruckType;
import lombok.Data;

import java.util.List;


@Data
public class FoodTruckModel {
    private String name;
    private FoodTruckType truckType;
    private double latitude;
    private double longitude;
    private List<FoodItem> foodItems;
}
