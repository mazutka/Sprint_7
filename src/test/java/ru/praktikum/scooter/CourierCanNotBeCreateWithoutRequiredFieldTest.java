package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
@RunWith(Parameterized.class)
public class CourierCanNotBeCreateWithoutRequiredFieldTest {
    private CourierClient courierClient;
    private final Courier courier;

    public CourierCanNotBeCreateWithoutRequiredFieldTest(Courier courier){
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                {CourierGenerator.getRandomWithoutLogin()},
                {CourierGenerator.getRandomWithoutPassword()}
        };
    }

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("API. POST '/courier'. Нельзя создать курьера без логина или пароля")
    @Step("Логин - {this.courier.login}/ Пароль - {this.courier.password}")
    public void CourierCanNotBeCreateWithoutRequiredField(){

        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        String message =createResponse.extract().path("message");
        assertEquals(statusCode,400);
        assertEquals(message,"Недостаточно данных для создания учетной записи");

    }

}
