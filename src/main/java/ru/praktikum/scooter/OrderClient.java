package ru.praktikum.scooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient{

    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String ORDER_TRACK_PATH = "/api/v1/orders/track?t=%s";
    private static final String ORDER_ACCEPT_PATH = "/api/v1/orders/accept/%s?courierId=%s";
    private static final String ORDERS_COURIER_PATH = "/api/v1/orders?courierId=%s";

   public ValidatableResponse order(Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    public ValidatableResponse accept(int courierId, int orderID){
        return given()
                .spec(getBaseSpec())
                .when()
                .put(String.format(ORDER_ACCEPT_PATH,orderID,courierId))
                .then();
    }

    public ValidatableResponse ordersByCourier(int courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(String.format(ORDERS_COURIER_PATH,courierId))
                .then();
    }

    public ValidatableResponse orderTrack(int orderTrack){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(String.format(ORDER_TRACK_PATH,orderTrack))
                .then();
    }
}
