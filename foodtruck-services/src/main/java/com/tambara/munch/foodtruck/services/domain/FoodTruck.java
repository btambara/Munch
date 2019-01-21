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

    @Enumerated(EnumType.ORDINAL)
    private FoodTruckType truckType;
    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public FoodTruckType getTruckType() {
        return truckType;
    }

    public void setTruckType(FoodTruckType truckType) {
        this.truckType = truckType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
