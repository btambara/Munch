package com.tambara.munch.foodtruck.services.repo;

import com.tambara.munch.foodtruck.services.domain.FoodItem;
import com.tambara.munch.foodtruck.services.domain.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByFoodTruck(FoodTruck foodTruck);
}
