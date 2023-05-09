package ru.praktikum.scooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient{
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private static final String COURIER_DELETE_PATH = "/api/v1/courier/%s";
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then();
    }

    public ValidatableResponse delete(int courierId){
        String json = String.format("{\n" +
                "    \"id\": \"%s\"\n" +
                "}",courierId);
        return given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .delete(String.format(COURIER_DELETE_PATH,courierId))
                .then();
    }
}
