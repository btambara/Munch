package com.tambara.munch.foodtruck.services.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;

@Entity
public class FoodTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long truckID;

    private String name;

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FoodItem> foodItems;

    public long getTruckID() {
        return truckID;
    }

    public void setTruckID(long truckID) {
        this.truckID = truckID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append('(');
        stringBuilder.append("truckID=");
        stringBuilder.append(getTruckID());
        stringBuilder.append(", name=");
        stringBuilder.append(getName());
        stringBuilder.append(", foodItems=[");
        if (getFoodItems().size() > 0) {
            Iterator<FoodItem> foodItemsItr = getFoodItems().iterator();
            while (foodItemsItr.hasNext()) {
                stringBuilder.append(foodItemsItr.next().toString());
                if (foodItemsItr.hasNext()) {
                    stringBuilder.append(", ");
                }
            }
        }
        stringBuilder.append("])");
        return stringBuilder.toString();
    }
}
