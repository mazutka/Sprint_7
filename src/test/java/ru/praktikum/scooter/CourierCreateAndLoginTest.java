package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CourierCreateAndLoginTest {

    private CourierClient courierClient;
    private final Courier courier;
    private int courierId;

    public CourierCreateAndLoginTest(Courier courier){
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                {CourierGenerator.getRandom()},
                {CourierGenerator.getRandomWithoutFirstName()}
        };
    }

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp(){
        courierClient.delete(courierId);
    }
    @Test
    @DisplayName("API. POST '/courier', POST /login. Создание курьера и успешный логин")
    @Step("Логин - {this.courier.login}/ Пароль - {this.courier.password}/ Имя - {this.courier.firstName}")
    public void courierCanBeCreatedAndLogged(){

        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated =createResponse.extract().path("ok");
        assertEquals(statusCode,201);
        assertTrue(isCourierCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertEquals(statusCode,200);
        assertNotEquals(0,courierId);

    }
}
