package com.tambara.munch.foodtruck.services.repo;

import com.tambara.munch.foodtruck.services.domain.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTruckRepo extends JpaRepository<FoodTruck, Long> {

}