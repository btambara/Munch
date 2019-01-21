package com.tambara.munch.foodtruck.services.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemID;

    private String name;

    private long price;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "truckID")
    @JsonBackReference
    private FoodTruck foodTruck;

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public FoodTruck getFoodTruck() {
        return foodTruck;
    }

    public void setFoodTruck(FoodTruck foodTruck) {
        this.foodTruck = foodTruck;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append('(');
        stringBuilder.append("itemID=");
        stringBuilder.append(getItemID());
        stringBuilder.append(", name=");
        stringBuilder.append(getName());
        stringBuilder.append(", price=");
        stringBuilder.append(getPrice());
        stringBuilder.append(", truckID=");
        stringBuilder.append(getFoodTruck().getTruckID());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}
