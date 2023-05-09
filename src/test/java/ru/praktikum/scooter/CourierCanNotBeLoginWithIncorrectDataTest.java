package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCanNotBeLoginWithIncorrectDataTest {

    private CourierClient courierClient;
    private CourierCredentials credentials;
    private int courierId;
    private final String field;
    private final String value;
    private final int expStatusCode;
    private final String expMessage;


    public CourierCanNotBeLoginWithIncorrectDataTest(String field,String value, int expStatusCode, String expMessage){
        this.field = field;
        this.value = value;
        this.expStatusCode = expStatusCode;
        this.expMessage = expMessage;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                {"password", RandomStringUtils.randomAlphabetic(10), SC_NOT_FOUND,"Учетная запись не найдена"},
                {"login", RandomStringUtils.randomAlphabetic(10), SC_NOT_FOUND, "Учетная запись не найдена"},
                {"password", null, SC_BAD_REQUEST, "Недостаточно данных для входа"},
                {"login", null, SC_BAD_REQUEST, "Недостаточно данных для входа"}
        };
    }

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        courierClient.createCourier(courier);
        credentials = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        courierId = loginResponse.extract().path("id");
    }

    @After
    public void cleanUp(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("API. POST '/login'. Вход невозможен с некорректным или отсутствующим логином или паролем")
    @Step("Отсутствующее поле - {this.field}/ Значение - {this.value}")
    public void CourierCanNotBeLoginWithIncorrectData(){
        if (field.equals("password")) {
            credentials.setPassword(value);
        } else {
            credentials.setLogin(value);
        }
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int actStatusCode = loginResponse.extract().statusCode();
        String actMessage = loginResponse.extract().path("message");
        assertEquals(expStatusCode, actStatusCode);
        assertEquals(expMessage, actMessage);

    }

}
