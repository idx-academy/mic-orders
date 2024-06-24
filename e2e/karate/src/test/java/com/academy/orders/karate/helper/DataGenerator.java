package com.academy.orders.karate.helper;

import com.github.javafaker.Faker;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static String generateEmail(){
        return faker.internet().emailAddress();
    }
}
