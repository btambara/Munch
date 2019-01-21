package com.tambara.munch.foodtruck.services.controller;

import com.tambara.munch.foodtruck.services.domain.FoodTruck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodTruckControllerTest {
    @Autowired
    private FoodTruckController foodTruckController;

    @Test
    public void shouldGetFoodTruck() {
        FoodTruck foodTruck = foodTruckController.getFoodTruck(1L);
        FoodTruck testFoodTruck = new FoodTruck();
        testFoodTruck.setName("Crazy Chicken Waffle");

        assertEquals(testFoodTruck.getName(), foodTruck.getName());
    }
}
