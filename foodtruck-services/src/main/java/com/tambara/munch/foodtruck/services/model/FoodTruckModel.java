package com.tambara.munch.foodtruck.services.model;

import com.tambara.munch.foodtruck.services.domain.FoodItem;
import lombok.Data;

import java.util.List;


@Data
public class FoodTruckModel {
    private String name;
    private List<FoodItem> foodItems;
}
