package ru.praktikum.scooter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderGenerator{

    public static Order getRandom(){
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(20);
        final String address = RandomStringUtils.randomAlphanumeric(30);
        final int metroStation = RandomUtils.nextInt(1,100);
        final String phone = RandomStringUtils.randomNumeric(11);
        final int rentTime = RandomUtils.nextInt(1,100);
        final String deliveryDate = String.format("%s-%s-%s",RandomUtils.nextInt(2023,2025),RandomUtils.nextInt(1,12),RandomUtils.nextInt(1,28));
        final String comment = RandomStringUtils.randomAlphabetic(50);
        final List<String> color = new ArrayList<>();
        switch (RandomUtils.nextInt(0,3)) {
            case 0:
                break;
            case 1:
                color.add("BLACK");
                break;
            case 2:
                color.add("GREY");
                break;
            case 3:
                color.add("BLACK");
                color.add("GREY");
                break;
        }
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
