INSERT INTO FOOD_TRUCK (NAME, TRUCK_TYPE, TIME_OPEN, TIME_CLOSED, LATITUDE, LONGITUDE)
    VALUES('Crazy Chicken Waffle', 0, 1000, 2400, 34.083170, -118.191783);
INSERT INTO FOOD_TRUCK (NAME, TRUCK_TYPE, TIME_OPEN, TIME_CLOSED, LATITUDE, LONGITUDE)
    VALUES('OMG Sushi', 1, 1700, 200, 34.08061, -118.178350);
INSERT INTO FOOD_TRUCK (NAME, TRUCK_TYPE, TIME_OPEN, TIME_CLOSED, LATITUDE, LONGITUDE)
    VALUES('King Taco', 3, 800, 2000, 34.059123, -118.199841);

INSERT INTO FOOD_ITEM (NAME, PRICE, TRUCKID) VALUES('Spicy Chicken and Waffle', 1000, 1);
INSERT INTO FOOD_ITEM (NAME, PRICE, TRUCKID) VALUES('Regular Chicken and Waffle', 900, 1);

INSERT INTO FOOD_ITEM (NAME, PRICE, TRUCKID) VALUES('Spicy Tuna', 200, 2);
INSERT INTO FOOD_ITEM (NAME, PRICE, TRUCKID) VALUES('OMG Roll', 500, 2);

INSERT INTO FOOD_ITEM (NAME, PRICE, TRUCKID) VALUES('Taco', 200, 3);
INSERT INTO FOOD_ITEM (NAME, PRICE, TRUCKID) VALUES('Torta', 500, 3);