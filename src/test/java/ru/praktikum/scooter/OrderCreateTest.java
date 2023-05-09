package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class OrderCreateTest {

    private OrderClient orderClient;
    private final String color;

    public OrderCreateTest(String color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                {""},
                {"GREY"},
                {"BLACK"},
                {"GREY,BLACK"}
        };
    }

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }
    @Test
    @DisplayName("API. POST '/orders'. Создание заказа")
    @Step("Цвет - {this.color}")
    public void orderCreate(){
        Order order = OrderGenerator.getRandom();
        order.setColor(Arrays.asList(color.split(",")));
        ValidatableResponse orderResponse = orderClient.order(order);
        int statusCode = orderResponse.extract().statusCode();
        int orderId = orderResponse.extract().path("track");
        assertEquals(statusCode,201);
        assertNotEquals(0,orderId);

    }
}
