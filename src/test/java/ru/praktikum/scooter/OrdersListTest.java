package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrdersListTest {

    private OrderClient orderClient;
    private CourierClient courierClient;
    private int courierId;
    @Before
    public void setUp(){
        //создаем курьера и запоминаем его id
        courierClient = new CourierClient();
        Courier courier = CourierGenerator.getRandom();
        courierClient.createCourier(courier);
        CourierCredentials credentials = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        courierId = loginResponse.extract().path("id");

        //в цикле создаем 5 заказов и назначаем их на курьера
        orderClient = new OrderClient();
        for (int i = 1; i < 6; i++) {
            Order order = OrderGenerator.getRandom();
            ValidatableResponse orderResponse = orderClient.order(order);
            int orderTrack = orderResponse.extract().path("track");
            ValidatableResponse orderTrackResponse = orderClient.orderTrack(orderTrack);
            int orderId = orderTrackResponse.extract().path("order.id");
            orderClient.accept(courierId, orderId);
        }
    }

    @After
    public void cleanUp(){
        courierClient.delete(courierId);
    }
    @Test
    @DisplayName("API. GET '/orders'. Получение списка заказов по курьеру")
    public void OrdersList(){
        ValidatableResponse responseOrdersList = orderClient.ordersByCourier(courierId);
        int statusCode = responseOrdersList.extract().statusCode();
        OrdersList actOrdersList = responseOrdersList.extract().body().as(OrdersList.class);
        assertEquals(SC_OK,statusCode);
        assertNotNull(actOrdersList);
    }
}
