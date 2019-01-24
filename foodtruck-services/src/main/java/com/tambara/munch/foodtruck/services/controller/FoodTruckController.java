package com.tambara.munch.foodtruck.services.controller;

import com.tambara.munch.foodtruck.services.domain.FoodItem;
import com.tambara.munch.foodtruck.services.domain.FoodItemNotFoundException;
import com.tambara.munch.foodtruck.services.domain.FoodTruck;
import com.tambara.munch.foodtruck.services.domain.FoodTruckNotFoundException;
import com.tambara.munch.foodtruck.services.model.FoodItemModel;
import com.tambara.munch.foodtruck.services.model.FoodTruckModel;
import com.tambara.munch.foodtruck.services.repo.FoodItemRepo;
import com.tambara.munch.foodtruck.services.repo.FoodTruckRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/foodtrucks")
public class FoodTruckController {
    public FoodTruckController(FoodTruckRepo foodTruckRepo, FoodItemRepo foodItemRepo) {
        this.foodTruckRepo = foodTruckRepo;
        this.foodItemRepo = foodItemRepo;
    }

    @GetMapping
    public List<FoodTruck> getAllOpenNearByFoodTrucks(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double distance, @RequestParam Double time) {
        Stream<FoodTruck> nearByOpenTrucksStream = foodTruckRepo.findAll().stream().
                filter(t -> getDistance(latitude, longitude, t.getLatitude(), t.getLongitude()) <= distance).
                filter(t -> t.getTimeOpen() <= time && t.getTimeClosed() >= time);

        ArrayList<FoodTruck> nearByOpenTrucksList = new ArrayList<>();
        nearByOpenTrucksStream.iterator().forEachRemaining(nearByOpenTrucksList::add);
        return nearByOpenTrucksList;
    }


    private static final double R = 6372.8; //KM

    private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    @GetMapping("/{truckID}")
    public FoodTruck getFoodTruck(@PathVariable Long truckID) {
        return doesTruckExist(truckID);
    }

    @GetMapping("/{truckID}/fooditems")
    public List<FoodItem> getAllTruckFoodItems(@PathVariable Long truckID) {
        return foodItemRepo.findByFoodTruck(doesTruckExist(truckID));
    }

    @GetMapping("/{truckID}/fooditems/{itemID}")
    public FoodItem getTruckFoodItem(@PathVariable Long truckID, @PathVariable Long itemID) {
        FoodTruck foodTruckDomain = doesTruckExist(truckID);
        FoodItem foodItemDomain = doesItemExist(itemID);
        if (foodTruckDomain.getFoodItems().stream().noneMatch(t -> t.getItemID() == itemID)) {
            throw new FoodItemNotFoundException("Food item ID does not belong to that truck ID: " + itemID);
        }

        return foodItemDomain;
    }

    @PostMapping
    public ResponseEntity<FoodTruck> addFoodTruck(@RequestBody FoodTruckModel model) {
        FoodTruck foodTruckDomain = new FoodTruck();
        foodTruckDomain.setName(model.getName());
        foodTruckDomain.setFoodItems(model.getFoodItems());
        if (foodTruckDomain.getFoodItems() != null && foodTruckDomain.getFoodItems().size() != 0) {
            for (FoodItem foodItem : foodTruckDomain.getFoodItems()) {
                foodItem.setFoodTruck(foodTruckDomain);
            }
        }


        FoodTruck newFoodTruckDomain = foodTruckRepo.save(foodTruckDomain);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{truckID}").buildAndExpand(newFoodTruckDomain.getTruckID()).toUri();
        return ResponseEntity.created(location).body(newFoodTruckDomain);
    }

    @PostMapping("/{truckID}/fooditems")
    public ResponseEntity<FoodItem> addFoodItemToTruck(@PathVariable Long truckID, @RequestBody FoodItemModel model) {
        FoodTruck foodTruckDomain = doesTruckExist(truckID);
        FoodItem foodItemDomain = new FoodItem();
        foodItemDomain.setFoodTruck(foodTruckDomain);
        foodItemDomain.setName(model.getName());
        foodItemDomain.setPrice(model.getPrice());

        FoodItem newFoodItemDomain = foodItemRepo.save(foodItemDomain);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{itemID}").buildAndExpand(newFoodItemDomain.getItemID()).toUri();
        return ResponseEntity.created(location).body(newFoodItemDomain);
    }

    @DeleteMapping("/{truckID}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteFoodTruck(@PathVariable Long truckID) {
        foodTruckRepo.delete(doesTruckExist(truckID));
    }

    @DeleteMapping("/{truckID}/fooditems/{itemID}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteTruckFoodItem(@PathVariable Long truckID, @PathVariable Long itemID) {
        FoodTruck foodTruckDomain = doesTruckExist(truckID);
        FoodItem foodItemDomain = doesItemExist(itemID);
        if (foodTruckDomain.getTruckID() == foodItemDomain.getFoodTruck().getTruckID()) {
            foodItemRepo.delete(foodItemDomain);
        }
    }

    @PutMapping("/{truckID}")
    public FoodTruck updateFoodTruck(@PathVariable Long truckID, @RequestBody FoodTruckModel model) {
        FoodTruck foodTruckDomain = doesTruckExist(truckID);
        FoodTruck foodTruckUpdated = new FoodTruck();

        foodTruckUpdated.setTruckID(foodTruckDomain.getTruckID());
        foodTruckUpdated.setName(model.getName());
        foodTruckUpdated.setFoodItems(model.getFoodItems());

        if (foodTruckUpdated.getFoodItems() != null && foodTruckUpdated.getFoodItems().size() != 0) {
            for (FoodItem foodItem : foodTruckUpdated.getFoodItems()) {
                foodItem.setFoodTruck(foodTruckUpdated);
            }
        }
        return foodTruckRepo.save(foodTruckUpdated);
    }

    @PutMapping("/{truckID}/fooditems/{itemID}")
    public FoodItem updateFoodItemForTruck(@PathVariable Long truckID, @PathVariable Long itemID, @RequestBody FoodItemModel model) {
        FoodTruck foodTruckDomain = doesTruckExist(truckID);
        FoodItem foodItemDomain = doesItemExist(itemID);
        if (foodTruckDomain.getFoodItems().stream().noneMatch(t -> t.getItemID() == itemID)) {
            throw new FoodItemNotFoundException("Food item ID does not belong to that truck ID: " + itemID);
        }

        return foodItemRepo.findById(itemID).map(foodItem ->
        {
            if (model.getName() != null) {
                foodItem.setName(model.getName());
            }
            if (model.getPrice() != 0) {
                foodItem.setPrice(model.getPrice());
            }
            return foodItemRepo.save(foodItem);
        }).get();
    }

    private FoodTruck doesTruckExist(Long truckID) {
        return foodTruckRepo.findById(truckID).
                orElseThrow(() -> new FoodTruckNotFoundException("No food truck belongs to that ID: " + truckID));
    }

    private FoodItem doesItemExist(Long itemID) {
        return foodItemRepo.findById(itemID).
                orElseThrow(() -> new FoodItemNotFoundException("No food item belongs to that ID: " + itemID));
    }

    private final FoodTruckRepo foodTruckRepo;
    private final FoodItemRepo foodItemRepo;
}
