package ru.praktikum.scooter;

import java.util.List;

public class OrdersList {
    private List<OrderInList> orders;

    public OrdersList() {
    }

    public List<OrderInList> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInList> orders) {
        this.orders = orders;
    }
}
