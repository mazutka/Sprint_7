package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.junit.Assert.assertEquals;

public class CourierCanNotBeCreateWithExistLoginTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }

    @After
    public void cleanUp(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("API. POST '/courier'. Нельзя создать уже существующего курьера")
    public void CourierCanNotBeCreateWithExistLogin(){
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        String message =createResponse.extract().path("message");
        assertEquals(SC_CONFLICT, statusCode);
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }


}
